package root.development.tms.features.userManagement.merchantManagement.domain.response;

import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.Merchant;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MerchantDetailsResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String contactPersonName;
    private List<Merchant.BankInfo> bankInfoList;
    private String totalOrders;
    private String deliveryAmount;

    public static MerchantDetailsResponse of(Merchant merchant) {
        MerchantDetailsResponse response = new MerchantDetailsResponse();
        response.setName(merchant.getName());
        response.setEmail(merchant.getEmail());
        response.setPhoneNumber(merchant.getPhoneNumber());
        response.setAddress(merchant.getAddress());
        response.setContractStartDate(merchant.getContractStartDate());
        response.setContractEndDate(merchant.getContractEndDate());
        response.setContactPersonName(merchant.getContactPersonName());
        response.setBankInfoList(merchant.getBankInfoList());
        return response;
    }

}
