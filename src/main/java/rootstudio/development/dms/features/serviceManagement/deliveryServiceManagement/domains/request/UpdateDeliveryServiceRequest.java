package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDeliveryServiceRequest {
    private String name;
    private String fromZoneId;
    private String toZoneId;
    private double price;
    private String description;
    private String iconUrl;
}
