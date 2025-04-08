package rootstudio.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
@Builder
public class DeliveryService extends BaseDocument {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String serviceId;

    private String description;
    private double price;
    private String iconUrl;

    @DocumentReference
    private Zone fromZone;

    @DocumentReference
    private Zone toZone;

}
