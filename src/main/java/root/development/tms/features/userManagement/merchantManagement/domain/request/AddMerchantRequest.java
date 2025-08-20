package root.development.tms.features.userManagement.merchantManagement.domain.request;

import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.Merchant;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddMerchantRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String contactPersonName;
    private List<Merchant.BankInfo> bankInfoList;
}
