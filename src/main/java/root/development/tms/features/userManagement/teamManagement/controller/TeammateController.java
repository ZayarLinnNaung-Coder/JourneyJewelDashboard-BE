package root.development.tms.features.userManagement.teamManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import root.development.tms.features.userManagement.teamManagement.domain.request.UpdatedTeammateRequest;
import root.development.tms.features.userManagement.teamManagement.domain.response.TeammateDetailsResponse;
import root.development.tms.features.userManagement.teamManagement.domain.response.TeammateResponse;
import root.development.tms.features.userManagement.teamManagement.domain.response.UpdatedTeammateResponse;
import root.development.tms.features.userManagement.teamManagement.service.TeammateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.userManagement.teamManagement.domain.request.TeammateRequest;
import root.development.tms.features.userManagement.teamManagement.domain.response.NewTeammateResponse;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import static root.development.tms.global.constants.SuccessCodeConstants.*;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

@RestController
@RequestMapping("/api/teammates")
@AllArgsConstructor
public class TeammateController extends BaseController {

    private final TeammateService teammateService;
    private final MessageBundle messageBundle;

    @GetMapping
    public Page<TeammateResponse> getTeammates(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String roleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return teammateService.getTeammates(query, roleId, pageable);
    }

    @GetMapping("/{id}")
    public TeammateDetailsResponse getTeammateById(@PathVariable String id) {
        return teammateService.getTeammateById(id);
    }
  
    @PostMapping
    public ResponseEntity<CustomResponse<NewTeammateResponse>> addTeammate(
            @RequestBody @Validated TeammateRequest teammateRequest) {
        NewTeammateResponse response = teammateService.addTeammate(teammateRequest);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SuccessCodeConstants.SUC_AUTH002),
                SUC_COM001
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteTeammate(@PathVariable String id) {
        teammateService.deleteTeammate(id);
        return createResponse(
                HttpStatus.OK,
                "null",
                messageBundle.getMessage(SuccessCodeConstants.SUC_COM001),
                SUC_COM001
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdatedTeammateResponse>> updateTeammate(
            @PathVariable("id") String id,
            @RequestBody UpdatedTeammateRequest updatedTeammate) {
        UpdatedTeammateResponse response = teammateService.updateTeammateRole(id, updatedTeammate);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SuccessCodeConstants.SUC_COM001),
                SUC_COM001
        );
    }

}
