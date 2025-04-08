package rootstudio.development.tms.features.userManagement.teamManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class TeammateResponse {

    private String id;
    private String username;
    private String email;
    private String roleName;
    private String lastDispatchTime;

    public static TeammateResponse of(Document teammate){
        return TeammateResponse.builder()
                .id(teammate.getObjectId("_id").toString())
                .username(teammate.getString("username"))
                .email(teammate.getString("email"))
                .roleName(teammate.getString("roleName"))
                .lastDispatchTime(
                        teammate.getDate("lastDispatchTime") != null ?
                                new SimpleDateFormat("yyyy-MM-dd").format(teammate.getDate("lastDispatchTime")) : "")
                .build();
    }

}
