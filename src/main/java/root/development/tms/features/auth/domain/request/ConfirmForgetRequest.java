package root.development.tms.features.auth.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmForgetRequest {
    private String email;
    private String otp;
}
