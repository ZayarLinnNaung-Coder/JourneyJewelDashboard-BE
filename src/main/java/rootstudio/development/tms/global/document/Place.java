package rootstudio.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class Place extends BaseDocument{

    @Id
    private String id;

    private String name;
    private String place;
    private Double minBudget;
    private String description;
    private String imageUrl;

}
