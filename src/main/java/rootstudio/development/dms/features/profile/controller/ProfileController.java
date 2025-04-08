package rootstudio.development.dms.features.profile.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.dms.features.profile.domain.response.UserProfileResponse;
import rootstudio.development.dms.features.profile.service.ProfileService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;
import static rootstudio.development.dms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.utils.MessageBundle;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class ProfileController extends BaseController {

    private final MessageBundle messageBundle;
    private final ProfileService profileService;
    @GetMapping
    ResponseEntity<CustomResponse<UserProfileResponse>> getLoginProfile(){
        return createResponse(HttpStatus.OK, profileService.getLoginProfile(), messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }


}
