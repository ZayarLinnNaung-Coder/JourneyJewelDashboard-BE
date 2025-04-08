package rootstudio.development.tms.features.userManagement.adminManagement.domain.request;

import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.AdminRole;

@Getter
@Setter
public class AdminRoleRequest {

    private String name;
    private String description;
    private String imageUrl;
    private AdminRole.AdminRolePermission permission;

}
