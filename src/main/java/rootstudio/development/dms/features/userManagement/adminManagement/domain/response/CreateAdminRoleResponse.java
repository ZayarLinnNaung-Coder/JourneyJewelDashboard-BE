package rootstudio.development.dms.features.userManagement.adminManagement.domain.response;

import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.AdminRole;


@Getter
@Setter
public class CreateAdminRoleResponse {
        private String id;
        private String name;
        private String description;
        private String imageUrl;
        private Long totalMembers;
        private AdminRole.AdminRolePermission permission;
}
