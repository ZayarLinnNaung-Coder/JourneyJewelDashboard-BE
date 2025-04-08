package rootstudio.development.tms.features.userManagement.adminManagement.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.AdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.UpdateAdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRolesDetailResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.CreateAdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.UpdateAdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.repo.AdminRoleRepository;
import rootstudio.development.tms.features.userManagement.adminManagement.service.AdminRoleService;
import rootstudio.development.tms.global.constants.ErrorCodeConstants;
import rootstudio.development.tms.global.constants.SuccessCodeConstants;
import rootstudio.development.tms.global.document.Admin;
import rootstudio.development.tms.global.document.AdminRole;
import rootstudio.development.tms.global.exception.model.BusinessException;
import rootstudio.development.tms.global.exception.model.NotFoundCommonException;
import rootstudio.development.tms.global.repo.AdminRepo;
import rootstudio.development.tms.global.utils.MessageBundle;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private MongoTemplate mongoTemplate;
    private AdminRoleRepository adminRoleRepository;
    private AdminRepo adminRepo;
    private final MessageBundle messageBundle;

    private static final Logger logger = LoggerFactory.getLogger(AdminRoleService.class);

    @Override
    public List<AdminRoleResponse> getAllAdminRoles() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("deleted").is(false)),
                Aggregation.lookup("admin", "_id", "role", "adminData"),

                Aggregation.project("id", "name", "description", "imageUrl")
                        .andExpression("size(adminData)").as("totalMembers")
        );

        List<Document> results = mongoTemplate.aggregate(aggregation, "adminRole", Document.class).getMappedResults();

        return results.stream()
                .map(AdminRoleResponse::of).toList();
    }


    @Override
    public CreateAdminRoleResponse createAdminRole(AdminRoleRequest request) {

        AdminRole newRole = AdminRole.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .permission(request.getPermission())
                .deleted(false)
                .build();

        AdminRole savedRole = mongoTemplate.save(newRole);

        CreateAdminRoleResponse response = new CreateAdminRoleResponse();
        response.setId(savedRole.getId());
        response.setName(savedRole.getName());
        response.setDescription(savedRole.getDescription());
        response.setImageUrl(savedRole.getImageUrl());
        response.setPermission(savedRole.getPermission());

        return response;
    }

    @Override
    public UpdateAdminRoleResponse updateAdminRole(String id, UpdateAdminRoleRequest request) {

        String roleNotFoundErr = MessageFormat.format(messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), "role", id);

        AdminRole adminRole = adminRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, roleNotFoundErr));

        adminRole.setName(request.getName());
        adminRole.setDescription(request.getDescription());
        adminRole.setImageUrl(request.getImageUrl());
        adminRole.setPermission(request.getPermission());
        adminRoleRepository.save(adminRole);

        return UpdateAdminRoleResponse.of(adminRole);
    }

    @Override
    public AdminRolesDetailResponse getAdminRoleById(String roleId) {

        String roleNotFoundErr = MessageFormat.format(messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), "role", roleId);

        AdminRole adminRole = adminRoleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, roleNotFoundErr));

        List<Admin> admins = adminRepo.findByRoleId(roleId);

        List<AdminRolesDetailResponse.AdminResponse> adminResponses = admins.stream()
                .map(admin -> new AdminRolesDetailResponse.AdminResponse(
                        admin.getId(),
                        admin.getUsername(),
                        admin.getEmail()
                ))
                .collect(Collectors.toList());

        return AdminRolesDetailResponse.of(adminRole, adminResponses);
    }

    @Override
    public void changeAdminRole(String id, String newRoleId) {
        try {
            if (StringUtils.isBlank(id)) {
                throw new BusinessException(ErrorCodeConstants.ERR_ADM001, messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001));
            }
            if (StringUtils.isBlank(newRoleId)) {
                throw new BusinessException(ErrorCodeConstants.ERR_ACC002, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC002));
            }

            AdminRole existingAdminRole = mongoTemplate.findById(id, AdminRole.class);
            if (existingAdminRole == null) {
                throw new NotFoundCommonException(ErrorCodeConstants.ERR_ACC003, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC003));
            }

            existingAdminRole.setDeleted(true);
            mongoTemplate.save(existingAdminRole);

            AdminRole newRole = mongoTemplate.findById(newRoleId, AdminRole.class);
            if (newRole == null) {
                throw new NotFoundCommonException(ErrorCodeConstants.ERR_PW004, messageBundle.getMessage(ErrorCodeConstants.ERR_PW004));
            }

            List<Admin> admins = adminRepo.findByRoleId(id);
            for (Admin admin : admins) {
                admin.setRole(newRole);
                mongoTemplate.save(admin);
            }

            mongoTemplate.save(newRole);

            logger.info(SuccessCodeConstants.SUC_COM001, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001));

        } catch (BusinessException | NotFoundCommonException e) {
            logger.error(ErrorCodeConstants.ERR_ADM001, messageBundle.getMessage(ErrorCodeConstants.ERR_ADM001));
            throw e;
        } catch (Exception e) {
            logger.error(ErrorCodeConstants.ERR_COM005, messageBundle.getMessage(ErrorCodeConstants.ERR_COM005), e);
            throw new BusinessException(ErrorCodeConstants.ERR_COM005, "Unexpected error occurred while changing admin roles: " + e.getMessage());
        }
    }

}