package rootstudio.development.dms.features.userManagement.adminManagement.domain.response;

import lombok.*;
import org.bson.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleResponse {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer totalMembers;

    public static AdminRoleResponse of(Document document){

        return AdminRoleResponse.builder()
                .id(document.getObjectId("_id").toString())
                .name(document.getString("name"))
                .description(document.getString("description"))
                .imageUrl(document.getString("imageUrl"))
                .totalMembers(document.getInteger("totalMembers"))
                .build();
    }
}