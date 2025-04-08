package rootstudio.development.tms.features.userManagement.teamManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewTeammateResponse {

    private String id;
    private String roleId;
    private String email;

}
