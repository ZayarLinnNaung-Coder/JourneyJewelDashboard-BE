package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rootstudio.development.dms.global.document.Township;

@Getter
@Setter
@Builder
public class TownshipResponse {
    private String id;
    private String name;

    public static TownshipResponse of(Township township) {
        return TownshipResponse.builder()
                .id(township.getId())
                .name(township.getName())
                .build();
    }

    public static TownshipResponse from(Document doc) {
        if (doc == null) return null;

        return TownshipResponse.builder()
                .id(doc.getObjectId("_id").toString())
                .name(doc.getString("name"))
                .build();
    }

}
