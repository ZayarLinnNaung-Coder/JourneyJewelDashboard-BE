package root.development.tms.global.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Packages extends BaseDocument{

    @Id
    private String id;

    private String name;
    private String description;
    private String merchantId;
    private String price;
    private String placeId;
    private String hotelId;
    private String transportationId;
    private String selectedRoomType;
    private String selectedMealPlan;

}