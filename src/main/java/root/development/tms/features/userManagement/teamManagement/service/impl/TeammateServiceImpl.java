package root.development.tms.features.userManagement.teamManagement.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import root.development.tms.features.mail.service.MailService;
import root.development.tms.features.mail.template.factory.MailTemplateFactory;
import root.development.tms.features.userManagement.teamManagement.domain.request.UpdatedTeammateRequest;
import root.development.tms.features.userManagement.teamManagement.domain.response.TeammateDetailsResponse;
import root.development.tms.features.userManagement.teamManagement.domain.response.TeammateResponse;
import root.development.tms.features.userManagement.teamManagement.domain.response.UpdatedTeammateResponse;
import root.development.tms.features.userManagement.teamManagement.service.TeammateService;
import root.development.tms.global.constants.ErrorCodeConstants;
import root.development.tms.global.constants.SuccessCodeConstants;
import root.development.tms.global.document.Admin;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import root.development.tms.features.userManagement.teamManagement.domain.request.TeammateRequest;
import root.development.tms.features.userManagement.teamManagement.domain.response.NewTeammateResponse;
import root.development.tms.global.document.AdminRole;
import root.development.tms.global.enumeration.AccountStatus;
import root.development.tms.global.exception.model.AccountNotFoundException;
import root.development.tms.global.exception.model.BusinessException;
import root.development.tms.global.exception.model.NotFoundCommonException;
import root.development.tms.global.repo.AdminRepo;
import root.development.tms.global.repo.AdminRoleRepo;
import root.development.tms.global.utils.MessageBundle;

@Service
@AllArgsConstructor
@Slf4j
public class TeammateServiceImpl implements TeammateService {

    private final MongoTemplate mongoTemplate;
    private final MessageBundle messageBundle;

    private final MailService emailService;

    private final AdminRepo adminRepo;
    private final AdminRoleRepo roleRepo;

    @Override
    public NewTeammateResponse addTeammate(TeammateRequest newTeammate) {

        String rawPassword = "123456";
        String encryptedPassword = new BCryptPasswordEncoder().encode(rawPassword);

        String roleNotFoundMessage = MessageFormat.format(ErrorCodeConstants.ERR_COM006, "Role", "id: "+ newTeammate.getRoleId());
        AdminRole role = roleRepo.findById(newTeammate.getRoleId())
                .orElseThrow(() -> new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, roleNotFoundMessage));


        Admin admin = Admin.builder()
                .role(role)
                .email(newTeammate.getEmail())
                .password(encryptedPassword)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        adminRepo.save(admin);

        Context context = new Context();
        context.setVariable("password", rawPassword);
        emailService.sendEmail(newTeammate.getEmail(), "New Teammate Invitation", MailTemplateFactory.produceInviteTeammateTemplate(context));

        // Construct and return the TeammateResponse
        return NewTeammateResponse.builder()
                .id(admin.getId())
                .roleId(newTeammate.getRoleId())
                .email(newTeammate.getEmail())
                .build();
    }

    @Override
    public Page<TeammateResponse> getTeammates(String query, String roleId, Pageable pageable) {
        
        Criteria criteria = Criteria.where("accountStatus").ne("DELETED");

        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("username").regex(query, "i"),
                    Criteria.where("email").regex(query, "i")
            );
        }

        if (roleId != null && !roleId.isEmpty()) {
            criteria.and("role").is(new ObjectId(roleId));  // Ensure ObjectId match
        }

        MatchOperation match = Aggregation.match(criteria);
        CountOperation count = Aggregation.count().as("total");

        Document countResult = mongoTemplate.aggregate(Aggregation.newAggregation(match, count), "admin", Document.class)
                .getUniqueMappedResult();

        long total = (countResult != null) ? countResult.get("total", Number.class).longValue() : 0L;

        Aggregation aggregation = Aggregation.newAggregation(
                match,
                Aggregation.lookup("adminRole", "role", "_id", "roleDetails"),
                Aggregation.unwind("roleDetails", true),
                Aggregation.project("id", "username", "email", "lastDispatchTime")
                        .and("roleDetails._id").as("roleId")
                        .and("roleDetails.name").as("roleName"),
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );

        List<TeammateResponse> responses = mongoTemplate.aggregate(aggregation, "admin", Document.class)
                .getMappedResults()
                .stream()
                .map(TeammateResponse::of)
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }

    @Override
    public void deleteTeammate(String id) {
        log.info(SuccessCodeConstants.SUC_COM001, "Deleting teammate with ID: {}", id);

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("accountStatus", AccountStatus.DELETED);

        try {
            var result = mongoTemplate.updateFirst(query, update, Admin.class);

            if (result.getModifiedCount() == 0) {
                log.warn(ErrorCodeConstants.ERR_ADM001, messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001));
                throw new BusinessException(ErrorCodeConstants.ERR_ADM001, messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001));
            }

            log.info(ErrorCodeConstants.ERR_ACC002, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC002));
        } catch (BusinessException e) {
            log.error(ErrorCodeConstants.ERR_ADM001, messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001));
            throw e;

        } catch (Exception e) {
            log.error(ErrorCodeConstants.ERR_COM000, messageBundle.getMessage(ErrorCodeConstants.ERR_COM000));
            throw new BusinessException(ErrorCodeConstants.ERR_COM000, messageBundle.getMessage(ErrorCodeConstants.ERR_COM000));
        }
    }


    @Override
    public UpdatedTeammateResponse updateTeammateRole(String id, UpdatedTeammateRequest updatedTeammate) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update().set("role", new ObjectId(updatedTeammate.getRoleId()));

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "admin");

        if (updateResult.getMatchedCount() > 0) {
            return UpdatedTeammateResponse.builder()
                    .id(id)
                    .roleId(updatedTeammate.getRoleId())
                    .build();
        } else {
            throw new BusinessException(ErrorCodeConstants.ERR_COM000, messageBundle.getMessage(ErrorCodeConstants.ERR_COM000));
        }
    }

    @Override
    public TeammateDetailsResponse getTeammateById(String id) {

        String accountNotFoundErrorMessage = MessageFormat.format(messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001), id);
        Admin teammate = adminRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, accountNotFoundErrorMessage));

        return TeammateDetailsResponse.of(teammate);
    }

}