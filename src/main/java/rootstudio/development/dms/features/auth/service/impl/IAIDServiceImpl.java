package rootstudio.development.dms.features.auth.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import rootstudio.development.dms.features.auth.domain.request.ResetDeviceRequest;
import rootstudio.development.dms.features.auth.service.IAIDService;
import rootstudio.development.dms.features.mail.service.MailService;
import rootstudio.development.dms.features.mail.template.factory.MailTemplateFactory;
import rootstudio.development.dms.features.auth.domain.request.ConfirmForgetRequest;
import rootstudio.development.dms.global.constants.ErrorCodeConstants;
import rootstudio.development.dms.global.constants.RedisKeyConstants;
import rootstudio.development.dms.global.document.Admin;
import rootstudio.development.dms.global.exception.model.AccountNotFoundException;
import rootstudio.development.dms.global.exception.model.InvalidOtpException;
import rootstudio.development.dms.global.exception.model.InvalidPasswordException;
import rootstudio.development.dms.global.repo.AdminRepo;
import rootstudio.development.dms.global.utils.JwtUtils;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.security.SecureRandom;
import java.time.Duration;

@Slf4j
@Service
@AllArgsConstructor
public class IAIDServiceImpl implements IAIDService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepo adminRepo;
    private final MessageBundle messageBundle;
    private final StringRedisTemplate redisTemplate;
    private final MailService mailService;

    @Override
    public void resetDeviceRequest(ResetDeviceRequest request) {
        long startTime = System.currentTimeMillis();
        Admin admin = adminRepo.findByEmail(request.getUsername())
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, "No such user with : " + request.getUsername()));
        System.out.println(System.currentTimeMillis() - startTime);

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new InvalidPasswordException(ErrorCodeConstants.ERR_PW004, "Password is incorrect");
        }
        String otp = generateSecureOTP();

        String otpKey = RedisKeyConstants.KEY_1A1D_REQUEST_VALID_TOKEN + admin.getId();
        redisTemplate.opsForValue().set(otpKey, otp, Duration.ofMinutes(15));

        Context context = new Context();
        context.setVariable("otp", otp);
        mailService.sendEmail(request.getUsername(), "Your OTP Code", MailTemplateFactory.produceOtpMailTemplate(context));
        log.info("OTP email sent to: {}", request.getUsername());
        log.info("OTP : {}", otp);
    }

    private String generateSecureOTP() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = secureRandom.nextInt(1_000_000);
        return String.format("%06d", otp);
    }

    @Override
    public void confirmForget(ConfirmForgetRequest request) {
        long startTime = System.currentTimeMillis();
        Admin admin = adminRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, "No such user with : " + request.getEmail()));
        System.out.println(System.currentTimeMillis() - startTime);

        String otpKey = RedisKeyConstants.KEY_1A1D_REQUEST_VALID_TOKEN + admin.getId();
        String storedOtp = redisTemplate.opsForValue().get(otpKey);
        log.info("Stored OTP from Redis: {}", storedOtp);
        log.info("Stored OTP: {}", storedOtp);
        log.info("Received OTP: {}", request.getOtp());

        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new InvalidOtpException(ErrorCodeConstants.ERR_OTP001, "Invalid OTP");
        }

        redisTemplate.delete(otpKey);
        String currentTokenKey = RedisKeyConstants.KEY_1A1D_CURRENT_TOKEN + admin.getId();
        redisTemplate.delete(currentTokenKey);
    }
}
