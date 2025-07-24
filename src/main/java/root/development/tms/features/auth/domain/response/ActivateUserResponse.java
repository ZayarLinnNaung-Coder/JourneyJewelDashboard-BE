package root.development.tms.features.auth.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.Admin;
import root.development.tms.global.document.AdminRole;
import root.development.tms.global.enumeration.AccountStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ActivateUserResponse {

    private String id;
    private String username;
    private String email;
    private LocalDateTime lastDispatchTime;
    private AccountStatus accountStatus;
    private AdminRole role;
    private String token;
    private String redisKey;

    public static ActivateUserResponse of(Admin admin, String jwt, String redisKey){
        return ActivateUserResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .token(jwt)
                .redisKey(redisKey)
                .accountStatus(admin.getAccountStatus())
                .lastDispatchTime(admin.getLastDispatchTime())
                .build();
    }

}
