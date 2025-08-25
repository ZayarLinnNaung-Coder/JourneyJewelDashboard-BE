package root.development.tms.features.auth.service;

import root.development.tms.features.auth.domain.request.ResetDeviceRequest;
import root.development.tms.features.auth.domain.request.ConfirmForgetRequest;

public interface IAIDService {
    void resetDeviceRequest(ResetDeviceRequest request);
    void confirmForget(ConfirmForgetRequest request);
}
