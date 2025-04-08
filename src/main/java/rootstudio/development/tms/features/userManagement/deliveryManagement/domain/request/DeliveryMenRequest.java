package rootstudio.development.tms.features.userManagement.deliveryManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryMenRequest {
    private String query;
    private int page = 0;
    private int size = 10;
}
