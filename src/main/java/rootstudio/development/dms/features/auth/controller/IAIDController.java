package rootstudio.development.dms.features.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rootstudio.development.dms.features.auth.domain.request.ConfirmForgetRequest;
import rootstudio.development.dms.features.auth.domain.request.ResetDeviceRequest;
import rootstudio.development.dms.features.auth.domain.response.ConfirmForgetResponse;
import rootstudio.development.dms.features.auth.domain.response.ResetDeviceResponse;
import rootstudio.development.dms.features.auth.service.IAIDService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.utils.MessageBundle;

import static rootstudio.development.dms.global.constants.SuccessCodeConstants.SUC_COM001;
@RestController
@RequestMapping("/api/1a1d")
@AllArgsConstructor
public class IAIDController extends BaseController {
    
    private final IAIDService IAIDService;
    private final MessageBundle messageBundle;

    @PostMapping("/reset/request")
    public ResponseEntity<CustomResponse<ResetDeviceResponse>> resetDeviceRequest(@RequestBody ResetDeviceRequest request) {
        IAIDService.resetDeviceRequest(request);
        return createResponse(HttpStatus.OK, null, messageBundle.getMessage(SUC_COM001), SUC_COM001);
    }

    @PostMapping("/reset/confirm")
    public ResponseEntity<CustomResponse<ConfirmForgetResponse>> confirmForgetAgent(@RequestBody ConfirmForgetRequest request) {
        IAIDService.confirmForget(request);
        return createResponse(HttpStatus.OK, null, messageBundle.getMessage(SUC_COM001), SUC_COM001);
    }
}
