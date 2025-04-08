package rootstudio.development.dms.features.userManagement.teamManagement.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdatedTeammateResponse {
    private String id;
    private String roleId;
}
