package rootstudio.development.dms.features.userManagement.deliveryManagement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.AddDeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.DeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.UpdateDeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.AddDeliveryMenResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.DeliveryMenDetailsResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.DeliveryMenResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.UpdateDeliveryMenResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.repo.DeliveryMenRepository;
import rootstudio.development.dms.features.userManagement.deliveryManagement.service.DeliveryMenService;
import rootstudio.development.dms.global.constants.ErrorCodeConstants;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;
import rootstudio.development.dms.global.document.DeliveryMen;
import rootstudio.development.dms.global.exception.model.*;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.text.MessageFormat;

@Service
@Slf4j
public class DeliveryMenServiceImpl implements DeliveryMenService {

    @Autowired
    DeliveryMenRepository deliveryMenRepository;
    @Autowired
    MessageBundle messageBundle;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public AddDeliveryMenResponse addDeliveryMen(AddDeliveryMenRequest request) {

        if (mongoTemplate.exists(new Query(Criteria.where("email").is(request.getEmail())), DeliveryMen.class)) {
            throw new EmailAlreadyExistsException(ErrorCodeConstants.ERR_DM0001, messageBundle.getMessage(ErrorCodeConstants.ERR_DM0001));
        }

        if (mongoTemplate.exists(new Query(Criteria.where("phoneNumber").is(request.getPhoneNumber())), DeliveryMen.class)) {
            throw new PhoneNumberAlreadyExistsException(ErrorCodeConstants.ERR_DM001, messageBundle.getMessage(ErrorCodeConstants.ERR_DM001));
        }

        String zoneId = StringUtils.isNotBlank(request.getZoneId()) ? request.getZoneId() : "DEFAULT_ZONE";
        Long totalRoutes = request.getTotalRoutes() != null ? request.getTotalRoutes() : 0L;

        DeliveryMen deliveryMen = DeliveryMen.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .vehicleType(request.getVehicleType())
                .zoneId(zoneId)
                .totalRoutes(totalRoutes)
                .build();

        DeliveryMen savedDeliveryMen = mongoTemplate.save(deliveryMen);

        return AddDeliveryMenResponse.of(savedDeliveryMen);
    }


    @Override
    public Page<DeliveryMenResponse> searchDeliveryMen(DeliveryMenRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<DeliveryMen> deliveryMen = deliveryMenRepository.searchByQuery(request.getQuery(), pageable);

        return deliveryMen.map(DeliveryMenResponse::of);
    }

    @Override
    public DeliveryMenDetailsResponse getDeliveryMenById(String id) {

        String accountNotFoundErrorMessage = MessageFormat.format(messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), "delivery men", id);

        Query query = new Query(Criteria.where("_id").is(id));
        DeliveryMen deliveryMen = mongoTemplate.findOne(query, DeliveryMen.class);

        if (deliveryMen == null) {
            throw new AccountNotFoundException(ErrorCodeConstants.ERR_DM001, accountNotFoundErrorMessage);
        }

        return DeliveryMenDetailsResponse.of(deliveryMen);
    }

    @Override
    public UpdateDeliveryMenResponse updateDeliveryMen(String id, UpdateDeliveryMenRequest request) {

        String deliveryMenNotFoundErr = MessageFormat.format(
                messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), id
        );

        Query query = new Query(Criteria.where("_id").is(id));
        DeliveryMen existingDeliveryMen = mongoTemplate.findOne(query, DeliveryMen.class);

        if (existingDeliveryMen == null) {
            throw new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, deliveryMenNotFoundErr);
        }

        existingDeliveryMen.setName(request.getName());
        existingDeliveryMen.setEmail(request.getEmail());
        existingDeliveryMen.setPhoneNumber(request.getPhoneNumber());
        existingDeliveryMen.setAddress(request.getAddress());
        existingDeliveryMen.setVehicleType(request.getVehicleType());
        existingDeliveryMen.setZoneId(request.getZoneId());
        existingDeliveryMen.setTotalRoutes(request.getTotalRoutes());

        try {
            mongoTemplate.save(existingDeliveryMen);
        } catch (DuplicateKeyException e) {
            String errorMessage = e.getMessage().toLowerCase();
            log.error("DuplicateKeyException occurred: {}", errorMessage);
            if (errorMessage.contains("email")) {
                throw new EmailAlreadyExistsException(ErrorCodeConstants.ERR_DM0001, messageBundle.getMessage(ErrorCodeConstants.ERR_DM0001));
            } else if (errorMessage.contains("phone")) {
                throw new PhoneNumberAlreadyExistsException(ErrorCodeConstants.ERR_DM001, messageBundle.getMessage(ErrorCodeConstants.ERR_DM001));
            }
            throw e;
        }


        return UpdateDeliveryMenResponse.of(existingDeliveryMen);
    }


    @Override
    public void deleteDeliveryMen(String id) {
        try {
            Query query = new Query(Criteria.where("_id").is(id));
            DeliveryMen deliveryMen = mongoTemplate.findOne(query, DeliveryMen.class);

            if (deliveryMen == null) {
                throw new NotFoundCommonException(ErrorCodeConstants.ERR_ACC003, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC003));
            }

            deliveryMen.setDeleted(true);
            mongoTemplate.save(deliveryMen);

            log.info(SuccessCodeConstants.SUC_COM001, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001));
        } catch (NotFoundCommonException e) {
            log.error(ErrorCodeConstants.ERR_ACC003, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC003));
            throw e;
        } catch (Exception e) {
            log.error(ErrorCodeConstants.ERR_COM005, messageBundle.getMessage(ErrorCodeConstants.ERR_COM005));
            throw new BusinessException(ErrorCodeConstants.ERR_COM005, messageBundle.getMessage(ErrorCodeConstants.ERR_COM005) + e.getMessage());
        }
    }

}
