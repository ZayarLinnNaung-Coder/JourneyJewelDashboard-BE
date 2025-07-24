package root.development.tms.features.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import root.development.tms.features.auth.domain.request.ActivateUserRequest;
import root.development.tms.features.auth.domain.request.LoginRequest;
import root.development.tms.features.auth.domain.response.ActivateUserResponse;
import root.development.tms.features.auth.domain.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    ActivateUserResponse activateUser(ActivateUserRequest request);

    String validateToken(String bearerToken);

}