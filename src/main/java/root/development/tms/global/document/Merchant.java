package root.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@Setter
@Builder
public class Merchant extends BaseDocument {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String phoneNumber;

    private String address;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String contactPersonName;
    private List<BankInfo> bankInfoList;

    @Getter
    @Setter
    public static class BankInfo {
        private String bankName;
        private String bankAccountHolderName;
        private String bankAccountNo;
        private String bankQR;
    }

}