package rootstudio.development.dms.features.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rootstudio.development.dms.features.auth.domain.request.ActivateUserRequest;
import rootstudio.development.dms.features.auth.domain.request.LoginRequest;
import rootstudio.development.dms.features.auth.domain.response.ActivateUserResponse;
import rootstudio.development.dms.features.auth.domain.response.LoginResponse;
import rootstudio.development.dms.features.auth.service.AuthService;
import rootstudio.development.dms.features.mail.service.MailService;
import rootstudio.development.dms.global.auth.domain.JwtRawInfo;
import rootstudio.development.dms.global.constants.DurationConstants;
import rootstudio.development.dms.global.constants.ErrorCodeConstants;
import rootstudio.development.dms.global.constants.RedisKeyConstants;
import rootstudio.development.dms.global.document.Admin;
import rootstudio.development.dms.global.enumeration.AccountStatus;
import rootstudio.development.dms.global.exception.model.*;
import rootstudio.development.dms.global.repo.AdminRepo;
import rootstudio.development.dms.global.utils.JwtUtils;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepo adminRepo;
    private final MessageBundle messageBundle;
    private final StringRedisTemplate redisTemplate;
    private final MailService mailService;

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request) {

        Admin admin = adminRepo.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001,"No Such User with" + loginRequest.getUsername()));

        if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())){
            throw new InvalidPasswordException(ErrorCodeConstants.ERR_PW004, messageBundle.getMessage(ErrorCodeConstants.ERR_PW004));
        }

        switch (admin.getAccountStatus()){
            case DELETED -> throw new AccountStatusException(ErrorCodeConstants.ERR_ACC002, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC002));
            case PENDING -> throw new ActivationNeedException(ErrorCodeConstants.ERR_ACC003, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC003));
        }

        String xAgentToken = request.getHeader("X-AGENT");

        String currentTokenKey = RedisKeyConstants.KEY_1A1D_CURRENT_TOKEN + admin.getId();
        String accountToken = redisTemplate.opsForValue().get(currentTokenKey);

        JwtRawInfo rawInfo = JwtRawInfo.builder()
                .userId(admin.getId())
                .build();
        String newJwtToken = jwtUtils.generateJwtToken(rawInfo);

        if (xAgentToken != null && xAgentToken.equals(accountToken)) {
            return LoginResponse.of(admin, newJwtToken, accountToken);
        } else if (accountToken != null) {
            throw new AlreadyLoggedInException(ErrorCodeConstants.ERR_AUTH002, messageBundle.getMessage(ErrorCodeConstants.ERR_AUTH002));
        }

        redisTemplate.opsForValue().set(currentTokenKey, newJwtToken, DurationConstants.ACCOUNT_TOKEN_EXPIRATION_MS, TimeUnit.MILLISECONDS);

        accountToken = generate1A1DKey();
        String otpKey = RedisKeyConstants.KEY_1A1D_CURRENT_TOKEN + admin.getId();
        redisTemplate.opsForValue().set(otpKey, accountToken, DurationConstants.ACCOUNT_TOKEN_EXPIRATION_MS, TimeUnit.MILLISECONDS);
        return LoginResponse.of(admin, newJwtToken, accountToken);
    }


    @Override
    public ActivateUserResponse activateUser(ActivateUserRequest request) {

        Admin admin = adminRepo.findByEmail(request.getMail())
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, request.getMail()));

        switch (admin.getAccountStatus()){
            case DELETED -> throw new AccountStatusException(ErrorCodeConstants.ERR_ACC002, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC002));
            case ACTIVE -> throw new AccountStatusException(ErrorCodeConstants.ERR_ACC005, messageBundle.getMessage(ErrorCodeConstants.ERR_ACC005));
        }

        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setUsername(request.getUsername());
        admin.setAccountStatus(AccountStatus.ACTIVE);
        adminRepo.save(admin);

        JwtRawInfo rawInfo = JwtRawInfo.builder()
                .userId(admin.getId())
                .build();
        String jwt = jwtUtils.generateJwtToken(rawInfo);

        String redis = generate1A1DKey();
        String redisKey = RedisKeyConstants.KEY_1A1D_PENDING_TOKEN + admin.getId();
        redisTemplate.opsForValue().set(redisKey, redis, 5, TimeUnit.MINUTES);

        return ActivateUserResponse.of(admin, jwt, redis);
    }

    @Override
    public String validateToken(String bearerToken) {
        jwtUtils.validateJwtToken(bearerToken);
        return "JWT is valid";
    }

    private Admin validateLoginRequest(LoginRequest loginRequest) {
        Admin admin = adminRepo.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, loginRequest.getUsername()));

        if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())){
            throw new InvalidPasswordException(ErrorCodeConstants.ERR_PW004, messageBundle.getMessage(ErrorCodeConstants.ERR_PW004));
        }
        return admin;
    }

    private String generate1A1DKey() {
        return UUID.randomUUID().toString();
    }

}
