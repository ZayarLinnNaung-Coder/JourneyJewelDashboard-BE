package rootstudio.development.tms.features.userManagement.merchantManagement.service;

import org.springframework.data.domain.Page;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.request.AddMerchantRequest;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.request.MerchantRequest;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.request.UpdateMerchantRequest;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.response.AddMerchantResponse;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.response.MerchantDetailsResponse;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.response.MerchantResponse;
import rootstudio.development.tms.features.userManagement.merchantManagement.domain.response.UpdateMerchantResponse;

public interface MerchantService {
    Page<MerchantResponse> searchMerchants(MerchantRequest request);
    AddMerchantResponse addMerchant(AddMerchantRequest request);
    UpdateMerchantResponse updateMerchant(String id, UpdateMerchantRequest request);
    void deleteMerchant(String id);

    MerchantDetailsResponse getMerchantDetails(String id);
}
