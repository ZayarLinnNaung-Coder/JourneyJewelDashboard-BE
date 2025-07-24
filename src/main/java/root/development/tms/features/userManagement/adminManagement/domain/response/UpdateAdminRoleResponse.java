package root.development.tms.features.userManagement.adminManagement.domain.response;

import lombok.Getter;
import lombok.Setter;
import root.development.tms.global.document.AdminRole;

@Getter
@Setter
public class UpdateAdminRoleResponse {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private AdminRole.AdminRolePermission permission;

    public static UpdateAdminRoleResponse of(AdminRole adminRole) {
        UpdateAdminRoleResponse response = new UpdateAdminRoleResponse();
        response.setId(adminRole.getId());
        response.setName(adminRole.getName());
        response.setDescription(adminRole.getDescription());
        response.setImageUrl(adminRole.getImageUrl());
        response.setPermission(adminRole.getPermission());

        return response;
    }

}
