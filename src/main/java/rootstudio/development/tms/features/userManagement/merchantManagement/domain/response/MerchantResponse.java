package rootstudio.development.tms.features.userManagement.merchantManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.Merchant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MerchantResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String contractStartDate;
    private String contractEndDate;

    public static MerchantResponse of(Merchant merchant){
        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .email(merchant.getEmail())
                .phoneNumber(merchant.getPhoneNumber())
                .address(merchant.getAddress())
                .contractStartDate(merchant.getContractStartDate() != null ? merchant.getContractStartDate().toString() : null)
                .contractEndDate(merchant.getContractEndDate() != null ? merchant.getContractEndDate().toString() : null)
                .build();
    }
}
