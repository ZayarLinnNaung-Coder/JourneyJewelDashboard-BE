package rootstudio.development.tms.features.userManagement.merchantManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddMerchantRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
}
