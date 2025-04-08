package rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.DeliveryMen;
import rootstudio.development.dms.global.enumeration.VehicleType;
@Builder
@Getter
@Setter
public class AddDeliveryMenResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private VehicleType vehicleType;
    private String zoneId;

    public static AddDeliveryMenResponse of(DeliveryMen deliveryMen){
        return AddDeliveryMenResponse.builder()
                .id(deliveryMen.getId())
                .name(deliveryMen.getName())
                .phoneNumber(deliveryMen.getPhoneNumber())
                .email(deliveryMen.getEmail())
                .address(deliveryMen.getAddress())
                .vehicleType(deliveryMen.getVehicleType())
                .zoneId(deliveryMen.getZoneId())
                .build();
    }
}
