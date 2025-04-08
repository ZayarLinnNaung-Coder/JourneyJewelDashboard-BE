package rootstudio.development.tms.features.userManagement.teamManagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.request.TeammateRequest;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.request.UpdatedTeammateRequest;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.response.NewTeammateResponse;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.response.TeammateDetailsResponse;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.response.TeammateResponse;
import rootstudio.development.tms.features.userManagement.teamManagement.domain.response.UpdatedTeammateResponse;

public interface TeammateService {
    NewTeammateResponse addTeammate(TeammateRequest newTeammate);
    Page<TeammateResponse> getTeammates(String query, String roleId, Pageable pageable);

    void deleteTeammate(String id);

    UpdatedTeammateResponse updateTeammateRole(String id, UpdatedTeammateRequest updatedTeammate);

    TeammateDetailsResponse getTeammateById(String id);
}
