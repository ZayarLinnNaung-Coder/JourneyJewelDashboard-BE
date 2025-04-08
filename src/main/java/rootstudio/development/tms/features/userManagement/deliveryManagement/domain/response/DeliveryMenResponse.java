package rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.DeliveryMen;
import rootstudio.development.tms.global.enumeration.DMAccountStatus;
import rootstudio.development.tms.global.enumeration.VehicleType;

@Builder
@Getter
@Setter
public class DeliveryMenResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private DMAccountStatus accountStatus;
    private VehicleType vehicleType;
    private String zoneId;
    private Long totalRoutes;

    public static DeliveryMenResponse of(DeliveryMen deliveryMen){
        return DeliveryMenResponse.builder()
                .id(deliveryMen.getId())
                .name(deliveryMen.getName())
                .email(deliveryMen.getEmail())
                .phoneNumber(deliveryMen.getPhoneNumber())
                .address(deliveryMen.getAddress())
                .accountStatus(deliveryMen.getAccountStatus())
                .vehicleType(deliveryMen.getVehicleType())
                .zoneId(deliveryMen.getZoneId())
                .totalRoutes(deliveryMen.getTotalRoutes())
                .build();
    }
}
