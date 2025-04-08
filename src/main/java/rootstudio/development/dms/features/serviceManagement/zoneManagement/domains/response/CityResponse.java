package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CityResponse {
    private String id;
    private String name;
    private List<TownshipResponse> townshipList;

    public static CityResponse of(Document cityDoc, List<Document> townships) {
        return CityResponse.builder()
                .id(cityDoc.getObjectId("_id").toString())
                .name(cityDoc.getString("name"))
                .townshipList(
                        townships.stream()
                                .filter(t -> cityDoc.getList("townships", ObjectId.class).contains(t.getObjectId("_id")))
                                .map(t -> TownshipResponse.builder()
                                        .id(t.getObjectId("_id").toString())
                                        .name(t.getString("name"))
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static CityResponse from(Document doc) {
        if (doc == null) {
            return CityResponse.builder()
                    .townshipList(Collections.emptyList())
                    .build();
        }

        return CityResponse.builder()
                .id(Optional.ofNullable(doc.getObjectId("_id"))
                        .map(ObjectId::toString)
                        .orElse(null))
                .name(doc.getString("name"))
                .townshipList(Optional.ofNullable(doc.getList("townships", Document.class))
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(tDoc -> TownshipResponse.builder()
                                .id(Optional.ofNullable(tDoc.getObjectId("_id"))
                                        .map(ObjectId::toString)
                                        .orElse(null))
                                .name(tDoc.getString("name"))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}