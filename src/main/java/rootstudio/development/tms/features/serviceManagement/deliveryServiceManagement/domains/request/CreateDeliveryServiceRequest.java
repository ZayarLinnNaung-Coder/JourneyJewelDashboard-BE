package rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDeliveryServiceRequest {
    private String name;
    private String fromZoneId;
    private String toZoneId;
    private double price;
    private String description;
    private String iconUrl;
}
