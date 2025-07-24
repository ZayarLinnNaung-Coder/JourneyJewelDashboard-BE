package root.development.tms.features.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import root.development.tms.features.auth.domain.request.ActivateUserRequest;
import root.development.tms.features.auth.domain.request.LoginRequest;
import root.development.tms.features.auth.domain.response.ActivateUserResponse;
import root.development.tms.features.auth.domain.response.LoginResponse;
import root.development.tms.features.auth.service.AuthService;
import root.development.tms.features.mail.service.MailService;
import root.development.tms.global.auth.domain.JwtRawInfo;
import root.development.tms.global.constants.ErrorCodeConstants;
import root.development.tms.global.constants.RedisKeyConstants;
import root.development.tms.global.document.Admin;
import root.development.tms.global.enumeration.AccountStatus;
import root.development.tms.global.exception.model.AccountNotFoundException;
import root.development.tms.global.exception.model.AccountStatusException;
import root.development.tms.global.exception.model.ActivationNeedException;
import root.development.tms.global.exception.model.InvalidPasswordException;
import root.development.tms.global.repo.AdminRepo;
import root.development.tms.global.utils.JwtUtils;
import root.development.tms.global.utils.MessageBundle;

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

        JwtRawInfo rawInfo = JwtRawInfo.builder()
                .userId(admin.getId())
                .build();
        String newJwtToken = jwtUtils.generateJwtToken(rawInfo);

        return LoginResponse.of(admin, newJwtToken, null);
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
