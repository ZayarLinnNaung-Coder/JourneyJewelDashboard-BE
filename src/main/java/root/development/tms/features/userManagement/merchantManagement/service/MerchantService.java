package root.development.tms.features.userManagement.merchantManagement.service;

import org.springframework.data.domain.Page;
import root.development.tms.features.userManagement.merchantManagement.domain.request.AddMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.MerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.UpdateMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.response.AddMerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantDetailsResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.UpdateMerchantResponse;

public interface MerchantService {
    Page<MerchantResponse> searchMerchants(MerchantRequest request);
    AddMerchantResponse addMerchant(AddMerchantRequest request);
    UpdateMerchantResponse updateMerchant(String id, UpdateMerchantRequest request);
    void deleteMerchant(String id);
    MerchantDetailsResponse getMerchantDetails(String id);
}
