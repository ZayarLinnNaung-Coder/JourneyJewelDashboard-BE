package rootstudio.development.tms.features.auth.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.Admin;
import rootstudio.development.tms.global.document.AdminRole;
import rootstudio.development.tms.global.enumeration.AccountStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String id;
    private String username;
    private String email;
    private LocalDateTime lastDispatchTime;
    private AccountStatus accountStatus;
    private AdminRole role;
    private String token;
    private String accountToken;

    public static LoginResponse of(Admin admin, String jwt, String accountToken){
        return LoginResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .token(jwt)
                .accountStatus(admin.getAccountStatus())
                .lastDispatchTime(admin.getLastDispatchTime())
                .accountToken(accountToken)
                .build();
    }

}
