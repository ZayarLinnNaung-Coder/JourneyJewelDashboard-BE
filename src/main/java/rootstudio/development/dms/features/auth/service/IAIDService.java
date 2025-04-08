package rootstudio.development.dms.features.auth.service;

import rootstudio.development.dms.features.auth.domain.request.ResetDeviceRequest;
import rootstudio.development.dms.features.auth.domain.request.ConfirmForgetRequest;

public interface IAIDService {
    void resetDeviceRequest(ResetDeviceRequest request);
    void confirmForget(ConfirmForgetRequest request);
}
