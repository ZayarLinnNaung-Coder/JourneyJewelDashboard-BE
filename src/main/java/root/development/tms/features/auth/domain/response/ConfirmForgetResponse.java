package root.development.tms.features.auth.domain.response;

import lombok.Data;
import root.development.tms.global.document.Admin;
@Data
public class ConfirmForgetResponse {
    private Admin admin;
    private String message;

    public static ConfirmForgetResponse of(Admin admin, String message) {
        ConfirmForgetResponse response = new ConfirmForgetResponse();
        response.setAdmin(admin);
        response.setMessage(message);
        return response;
    }
}
