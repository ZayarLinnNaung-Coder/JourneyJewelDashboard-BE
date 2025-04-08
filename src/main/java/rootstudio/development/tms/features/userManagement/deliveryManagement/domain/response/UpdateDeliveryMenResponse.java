package rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.DeliveryMen;
import rootstudio.development.tms.global.enumeration.VehicleType;
@Builder
@Getter
@Setter
public class UpdateDeliveryMenResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private VehicleType vehicleType;

    public static UpdateDeliveryMenResponse of(DeliveryMen deliveryMen){
        return UpdateDeliveryMenResponse.builder()
                .id(deliveryMen.getId())
                .name(deliveryMen.getName())
                .phoneNumber(deliveryMen.getPhoneNumber())
                .email(deliveryMen.getEmail())
                .address(deliveryMen.getAddress())
                .vehicleType(deliveryMen.getVehicleType())
                .build();
    }
}
