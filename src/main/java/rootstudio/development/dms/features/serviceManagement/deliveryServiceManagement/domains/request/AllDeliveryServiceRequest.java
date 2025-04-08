package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllDeliveryServiceRequest {

    private String query;
    private int size;
    private int page;
}
