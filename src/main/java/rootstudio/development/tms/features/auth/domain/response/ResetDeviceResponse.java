package rootstudio.development.tms.features.auth.domain.response;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ResetDeviceResponse {

    private Map<String, Object> response;
    public static ResetDeviceResponse of(Map<String, Object> response) {
        ResetDeviceResponse resetDeviceResponse = new ResetDeviceResponse();
        resetDeviceResponse.response = response;
        return resetDeviceResponse;
    }

}