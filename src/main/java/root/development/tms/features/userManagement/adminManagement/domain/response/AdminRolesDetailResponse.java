package root.development.tms.features.userManagement.adminManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import root.development.tms.global.document.AdminRole;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRolesDetailResponse {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private AdminRole.AdminRolePermission permission;
    private List<AdminResponse> admins; // List of admins under this role

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminResponse {
        private String adminId;
        private String username;
        private String email;

        public static AdminResponse of(String adminId, String username, String email) {
            return new AdminResponse(adminId, username, email);
        }
    }

    public static AdminRolesDetailResponse of(AdminRole role, List<AdminResponse> admins) {
        AdminRolesDetailResponse response = new AdminRolesDetailResponse();

        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        response.setImageUrl(role.getImageUrl());
        response.setPermission(role.getPermission());
        response.setAdmins(admins);
        return response;
    }

}
