package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
@Builder
public class AllDeliveryServiceResponse {

    private String id;
    private String serviceId;
    private String name;
    private double price;
    private String description;
    private String iconUrl;

    public static AllDeliveryServiceResponse of (Document document){
        return AllDeliveryServiceResponse.builder()
                .id(document.getObjectId("_id").toString())
                .serviceId(document.getString("serviceId"))
                .name(document.getString("name"))
                .price(document.getDouble("price"))
                .description(document.getString("description"))
                .iconUrl(document.getString("iconUrl"))
                .build();
    }
}
