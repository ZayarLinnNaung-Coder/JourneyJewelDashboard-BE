package root.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import root.development.tms.global.enumeration.PlaceType;

import java.util.List;

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
    private PlaceType placeType;
    private String imageUrl;
    private List<AdditionalImage> additionalImages;

    @Getter
    @Setter
    public static class AdditionalImage{
        private String url;
    }

}
