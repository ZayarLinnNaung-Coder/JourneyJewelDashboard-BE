package root.development.tms.features.userManagement.merchantManagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import root.development.tms.features.userManagement.merchantManagement.domain.request.AddMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.MerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.UpdateMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.response.AddMerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantDetailsResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.UpdateMerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.repo.MerchantRepository;
import root.development.tms.features.userManagement.merchantManagement.service.MerchantService;
import root.development.tms.global.constants.ErrorCodeConstants;
import root.development.tms.global.constants.SuccessCodeConstants;
import root.development.tms.global.document.Merchant;
import root.development.tms.global.exception.model.*;
import root.development.tms.global.utils.MessageBundle;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    MessageBundle messageBundle;

    @Override
    public AddMerchantResponse addMerchant(AddMerchantRequest request) {

        if (mongoTemplate.exists(new Query(Criteria.where("email").is(request.getEmail())), Merchant.class)) {
            throw new EmailAlreadyExistsException(ErrorCodeConstants.ERR_ME0001, messageBundle.getMessage(ErrorCodeConstants.ERR_ME0001));
        }

        if (mongoTemplate.exists(new Query(Criteria.where("phoneNumber").is(request.getPhoneNumber())), Merchant.class)) {
            throw new PhoneNumberAlreadyExistsException(ErrorCodeConstants.ERR_ME001, messageBundle.getMessage(ErrorCodeConstants.ERR_ME001));
        }

        Merchant merchant = Merchant.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .contractStartDate(request.getContractStartDate())
                .contractEndDate(request.getContractEndDate())
                .contactPersonName(request.getContactPersonName())
                .build();

        Merchant savedMerchant = mongoTemplate.save(merchant);

        return AddMerchantResponse.of(savedMerchant);
    }

    @Override
    public Page<MerchantResponse> searchMerchants(MerchantRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Merchant> merchants = merchantRepository.searchByQuery(request.getQuery(), pageable);

        if (merchants.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, merchants.getTotalElements());
        }

        // Create response list
        List<MerchantResponse> responses = merchants.stream()
                .map(merchant -> {
                    return MerchantResponse.of(merchant, 0, 0);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, merchants.getTotalElements());
    }

    @Override
    public UpdateMerchantResponse updateMerchant(String id, UpdateMerchantRequest request) {

        String merchantNotFoundErr = MessageFormat.format(
                messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), id
        );

        Query query = new Query(Criteria.where("_id").is(id));
        Merchant existingMerchant = mongoTemplate.findOne(query, Merchant.class);

        if (existingMerchant == null) {
            throw new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, merchantNotFoundErr);
        }

        setIfNotEmpty(request.getName(), existingMerchant::setName);
        setIfNotEmpty(request.getEmail(), existingMerchant::setEmail);
        setIfNotEmpty(request.getPhoneNumber(), existingMerchant::setPhoneNumber);
        setIfNotEmpty(request.getAddress(), existingMerchant::setAddress);
        setIfNotEmpty(request.getContractStartDate(), existingMerchant::setContractStartDate);
        setIfNotEmpty(request.getContractEndDate(), existingMerchant::setContractEndDate);
        setIfNotEmpty(request.getContactPersonName(), existingMerchant::setContactPersonName);

        try {
            mongoTemplate.save(existingMerchant);
        } catch (DuplicateKeyException e) {
            String errorMessage = e.getMessage().toLowerCase();
            if (errorMessage.contains("email")) {
                throw new EmailAlreadyExistsException(ErrorCodeConstants.ERR_ME0001, messageBundle.getMessage(ErrorCodeConstants.ERR_ME0001));
            } else if (errorMessage.contains("phoneNumber")) {
                throw new PhoneNumberAlreadyExistsException(ErrorCodeConstants.ERR_ME001, messageBundle.getMessage(ErrorCodeConstants.ERR_ME001));
            }
            throw e;
        }
        return UpdateMerchantResponse.of(existingMerchant);
    }

    private <T> void setIfNotEmpty(T value, Consumer<T> setter) {
        if (value instanceof String str) {
            if (!str.isEmpty()) setter.accept(value);
        } else if (value != null) {
            setter.accept(value);
        }
    }

    @Override
    public void deleteMerchant(String id) {
        try {
            Query query = new Query(Criteria.where("_id").is(id));
            Merchant merchant = mongoTemplate.findAndRemove(query, Merchant.class);

            if (merchant == null) {
                throw new NotFoundCommonException(ErrorCodeConstants.ERR_ACC003, "Merchant not found.");
            }

            log.info(SuccessCodeConstants.SUC_COM001, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), id);
        } catch (NotFoundCommonException e) {
            log.error(ErrorCodeConstants.ERR_ACC003, "Error occurred while deleting merchant: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(ErrorCodeConstants.ERR_COM005, "Unexpected error occurred while deleting merchant", e);
            throw new BusinessException(ErrorCodeConstants.ERR_COM005, "Unexpected error occurred while deleting merchant: " + e.getMessage());
        }
    }

    @Override
    public MerchantDetailsResponse getMerchantDetails(String id) {
        String merchantNotFoundMessage = MessageFormat.format(messageBundle.getMessage(ErrorCodeConstants.ERR_COM006), "merchant", id);

        Query query = new Query(Criteria.where("_id").is(id));
        Merchant merchant = mongoTemplate.findOne(query, Merchant.class);

        if (merchant == null) {
            throw new AccountNotFoundException(ErrorCodeConstants.ERR_DM001, merchantNotFoundMessage);
        }

        return MerchantDetailsResponse.of(merchant);
    }

}
