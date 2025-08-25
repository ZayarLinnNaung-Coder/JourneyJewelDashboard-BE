package root.development.tms.features.profile.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.profile.domain.response.UserProfileResponse;
import root.development.tms.features.profile.service.ProfileService;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import static root.development.tms.global.constants.SuccessCodeConstants.*;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.utils.MessageBundle;

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
