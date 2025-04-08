package rootstudio.development.dms.features.auth.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetDeviceRequest {
    private String username;
    private String password;
}
