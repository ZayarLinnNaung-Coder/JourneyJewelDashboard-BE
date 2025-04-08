package rootstudio.development.tms.features.userManagement.deliveryManagement.domain.request;

import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.enumeration.VehicleType;
@Getter
@Setter
public class AddDeliveryMenRequest {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private VehicleType vehicleType;
    private String zoneId;
    private String isDeleted;
    private Long totalRoutes;

}
