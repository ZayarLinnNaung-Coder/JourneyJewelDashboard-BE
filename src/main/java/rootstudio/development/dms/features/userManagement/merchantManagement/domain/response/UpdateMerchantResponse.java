package rootstudio.development.dms.features.userManagement.merchantManagement.domain.response;

import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.Merchant;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateMerchantResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    public static UpdateMerchantResponse of(Merchant merchant) {
        UpdateMerchantResponse response = new UpdateMerchantResponse();
        response.setName(merchant.getName());
        response.setEmail(merchant.getEmail());
        response.setPhoneNumber(merchant.getPhoneNumber());
        response.setAddress(merchant.getAddress());
        response.setContractStartDate(merchant.getContractStartDate());
        response.setContractEndDate(merchant.getContractEndDate());
        return response;
    }
}
