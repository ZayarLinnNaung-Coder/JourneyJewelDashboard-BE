package rootstudio.development.tms.features.auth.service;

import rootstudio.development.tms.features.auth.domain.request.ResetDeviceRequest;
import rootstudio.development.tms.features.auth.domain.request.ConfirmForgetRequest;

public interface IAIDService {
    void resetDeviceRequest(ResetDeviceRequest request);
    void confirmForget(ConfirmForgetRequest request);
}
