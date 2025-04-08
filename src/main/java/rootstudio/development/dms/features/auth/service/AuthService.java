package rootstudio.development.dms.features.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import rootstudio.development.dms.features.auth.domain.request.ActivateUserRequest;
import rootstudio.development.dms.features.auth.domain.request.LoginRequest;
import rootstudio.development.dms.features.auth.domain.response.ActivateUserResponse;
import rootstudio.development.dms.features.auth.domain.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    ActivateUserResponse activateUser(ActivateUserRequest request);

    String validateToken(String bearerToken);

}