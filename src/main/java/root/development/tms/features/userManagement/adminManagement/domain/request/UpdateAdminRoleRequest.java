package root.development.tms.features.userManagement.adminManagement.domain.request;

import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.AdminRole;

@Getter
@Setter
public class UpdateAdminRoleRequest {

    private String name;
    private String description;
    private String imageUrl;
    private AdminRole.AdminRolePermission permission;

}