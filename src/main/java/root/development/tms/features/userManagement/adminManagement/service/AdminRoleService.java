package root.development.tms.features.userManagement.adminManagement.service;

import root.development.tms.features.userManagement.adminManagement.domain.request.AdminRoleRequest;
import root.development.tms.features.userManagement.adminManagement.domain.request.UpdateAdminRoleRequest;
import root.development.tms.features.userManagement.adminManagement.domain.response.AdminRoleResponse;
import root.development.tms.features.userManagement.adminManagement.domain.response.AdminRolesDetailResponse;
import root.development.tms.features.userManagement.adminManagement.domain.response.CreateAdminRoleResponse;
import root.development.tms.features.userManagement.adminManagement.domain.response.UpdateAdminRoleResponse;

import java.util.List;

public interface AdminRoleService {

    List<AdminRoleResponse> getAllAdminRoles();
    CreateAdminRoleResponse createAdminRole(AdminRoleRequest request);
    UpdateAdminRoleResponse updateAdminRole(String id, UpdateAdminRoleRequest request);
    AdminRolesDetailResponse getAdminRoleById(String roleId);
    void changeAdminRole(String id, String newRoleId);
}
