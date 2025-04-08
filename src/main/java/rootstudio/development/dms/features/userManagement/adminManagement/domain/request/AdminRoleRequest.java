package rootstudio.development.dms.features.userManagement.adminManagement.domain.request;

import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.AdminRole;

@Getter
@Setter
public class AdminRoleRequest {

    private String name;
    private String description;
    private String imageUrl;
    private AdminRole.AdminRolePermission permission;

}
