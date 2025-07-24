package root.development.tms.features.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.auth.domain.request.ActivateUserRequest;
import root.development.tms.features.auth.domain.request.LoginRequest;
import root.development.tms.features.auth.domain.response.ActivateUserResponse;
import root.development.tms.features.auth.domain.response.LoginResponse;
import root.development.tms.features.auth.service.AuthService;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

import static root.development.tms.global.constants.SuccessCodeConstants.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;
    private final MessageBundle messageBundle;

    @PostMapping("/login")
    ResponseEntity<CustomResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        LoginResponse response = authService.login(loginRequest, request);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("X-AGENT", response.getAccountToken());
        return createResponse(HttpStatus.OK, headers, response, messageBundle.getMessage(SuccessCodeConstants.SUC_AUTH001), SUC_AUTH001);
    }


    @PostMapping("/activate-user")
    ResponseEntity<CustomResponse<ActivateUserResponse>> activateUser(@RequestBody ActivateUserRequest request){
        return createResponse(HttpStatus.OK, authService.activateUser(request), messageBundle.getMessage(SuccessCodeConstants.SUC_AUTH001), SUC_AUTH002);
    }

    @PostMapping("/validate-token")
    ResponseEntity<CustomResponse<String>> validateToken(
            @RequestHeader("Authorization") String bearerToken
    ){
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        return createResponse(HttpStatus.OK, authService.validateToken(bearerToken), messageBundle.getMessage(SuccessCodeConstants.SUC_JWT003), SUC_JWT003);
    }


}
