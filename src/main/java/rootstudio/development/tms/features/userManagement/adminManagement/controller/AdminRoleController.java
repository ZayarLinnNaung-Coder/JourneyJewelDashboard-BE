package rootstudio.development.tms.features.userManagement.adminManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.AdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.request.UpdateAdminRoleRequest;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.AdminRolesDetailResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.CreateAdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.domain.response.UpdateAdminRoleResponse;
import rootstudio.development.tms.features.userManagement.adminManagement.service.AdminRoleService;
import rootstudio.development.tms.global.BaseController;
import rootstudio.development.tms.global.constants.SuccessCodeConstants;
import static rootstudio.development.tms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.tms.global.domain.CustomResponse;
import rootstudio.development.tms.global.utils.MessageBundle;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-roles")
public class AdminRoleController extends BaseController {

    private final AdminRoleService adminRoleService;
    private final MessageBundle messageBundle;
    @GetMapping
    public ResponseEntity<CustomResponse<List<AdminRoleResponse>>> getAllAdminRoles() {
        List<AdminRoleResponse> adminRoles = adminRoleService.getAllAdminRoles();
        return createResponse(HttpStatus.OK, adminRoles, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001 );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<CreateAdminRoleResponse>> createAdminRole(@RequestBody AdminRoleRequest request) {
        CreateAdminRoleResponse adminRoleResponse = adminRoleService.createAdminRole(request);
        return createResponse(HttpStatus.CREATED, adminRoleResponse, messageBundle.getMessage(SuccessCodeConstants.SUC_AUTH002), SUC_AUTH002);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateAdminRoleResponse>> updateAdminRole(
            @PathVariable String id,
            @RequestBody UpdateAdminRoleRequest request) {

        UpdateAdminRoleResponse updatedAdminRole = adminRoleService.updateAdminRole(id, request);
        return createResponse(HttpStatus.OK, updatedAdminRole, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<CustomResponse<AdminRolesDetailResponse>> getAdminRoleById(@PathVariable String roleId) {
        AdminRolesDetailResponse response = adminRoleService.getAdminRoleById(roleId);
        return createResponse(HttpStatus.OK, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> changeAdminRole(
            @PathVariable String id,
            @RequestParam String newRoleId) {
        adminRoleService.changeAdminRole(id, newRoleId);
        return createResponse(HttpStatus.OK, null, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001 );
    }

}