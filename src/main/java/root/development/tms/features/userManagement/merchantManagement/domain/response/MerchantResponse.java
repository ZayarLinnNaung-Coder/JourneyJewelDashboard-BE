package root.development.tms.features.userManagement.merchantManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.Merchant;

import java.util.List;

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
    private String contachPersonName;
    private List<Merchant.BankInfo> bankInfoList;
    // added these two
    private String totalOrders;
    private String deliveryAmount;

    public static MerchantResponse of(Merchant merchant, int totalOrders,  double deliveryAmount) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .email(merchant.getEmail())
                .phoneNumber(merchant.getPhoneNumber())
                .address(merchant.getAddress())
                .contractStartDate(merchant.getContractStartDate() != null ? merchant.getContractStartDate().toString() : null)
                .contractEndDate(merchant.getContractEndDate() != null ? merchant.getContractEndDate().toString() : null)
                .contachPersonName(merchant.getContactPersonName())
                .bankInfoList(merchant.getBankInfoList())
                .totalOrders(String.valueOf(totalOrders))
                .deliveryAmount(String.valueOf(deliveryAmount))
                .build();
    }
}
