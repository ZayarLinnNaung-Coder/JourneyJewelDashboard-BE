package rootstudio.development.tms.features.userManagement.adminManagement.service;

import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.AdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.UpdateAdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRolesDetailResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.CreateAdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.UpdateAdminRoleResponse;

import java.util.List;

public interface AdminRoleService {

    List<AdminRoleResponse> getAllAdminRoles();
    CreateAdminRoleResponse createAdminRole(AdminRoleRequest request);
    UpdateAdminRoleResponse updateAdminRole(String id, UpdateAdminRoleRequest request);
    AdminRolesDetailResponse getAdminRoleById(String roleId);
    void changeAdminRole(String id, String newRoleId);
}
