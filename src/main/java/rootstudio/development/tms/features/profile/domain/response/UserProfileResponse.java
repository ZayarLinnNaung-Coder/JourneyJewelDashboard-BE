package rootstudio.development.tms.features.profile.domain.response;

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
public class UserProfileResponse {

    private String id;
    private String username;
    private String email;
    private LocalDateTime lastDispatchTime;
    private AccountStatus accountStatus;
    private AdminRole role;
    private String token;

    public static UserProfileResponse of(Admin admin){
        return UserProfileResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .accountStatus(admin.getAccountStatus())
                .lastDispatchTime(admin.getLastDispatchTime())
                .build();
    }

}
