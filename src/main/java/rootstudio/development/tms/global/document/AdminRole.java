package rootstudio.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import rootstudio.development.tms.global.enumeration.PermissionType;

import java.util.List;

@Document
@Getter
@Setter
@Builder
public class AdminRole extends BaseDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;
    private String imageUrl;
    private AdminRolePermission permission;
    private boolean deleted;

    @Getter
    @Setter
    @Builder
    public static class AdminRolePermission extends BaseDocument{

        private List<MenuPermission> menuPermissions;

        @Getter
        @Setter
        @Builder
        public static class MenuPermission{

            private String menuId;
            private List<PermissionType> permissionTypes;
        }

    }

}
