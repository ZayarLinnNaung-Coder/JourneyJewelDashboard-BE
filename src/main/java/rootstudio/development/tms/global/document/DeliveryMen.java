package rootstudio.development.tms.global.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import rootstudio.development.tms.global.enumeration.DMAccountStatus;
import rootstudio.development.tms.global.enumeration.VehicleType;

@Document
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeliveryMen extends BaseDocument {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String phoneNumber;

    private String address;

    private DMAccountStatus accountStatus;

    private VehicleType vehicleType;
    private boolean isDeleted;

    private String zoneId;

//    private String zone;
    private Long totalRoutes;


    public DeliveryMen() {
        this.zoneId = "DEFAULT_ZONE";
        this.totalRoutes = 0L;
    }
}
