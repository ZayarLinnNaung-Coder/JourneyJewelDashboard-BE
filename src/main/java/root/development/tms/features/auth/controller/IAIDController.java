package root.development.tms.features.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.development.tms.features.auth.domain.request.ConfirmForgetRequest;
import root.development.tms.features.auth.domain.request.ResetDeviceRequest;
import root.development.tms.features.auth.domain.response.ConfirmForgetResponse;
import root.development.tms.features.auth.domain.response.ResetDeviceResponse;
import root.development.tms.features.auth.service.IAIDService;
import root.development.tms.global.BaseController;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

import static root.development.tms.global.constants.SuccessCodeConstants.SUC_COM001;
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
