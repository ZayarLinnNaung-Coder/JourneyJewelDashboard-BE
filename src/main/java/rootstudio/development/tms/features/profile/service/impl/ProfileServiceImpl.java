package rootstudio.development.tms.features.profile.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rootstudio.development.tms.features.profile.domain.response.UserProfileResponse;
import rootstudio.development.tms.features.profile.service.ProfileService;
import rootstudio.development.tms.global.constants.ErrorCodeConstants;
import rootstudio.development.tms.global.document.Admin;
import rootstudio.development.tms.global.exception.model.AccountNotFoundException;
import rootstudio.development.tms.global.repo.AdminRepo;
import rootstudio.development.tms.global.utils.HttpRequestUtils;
import rootstudio.development.tms.global.utils.MessageBundle;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final AdminRepo adminRepo;
    private final MessageBundle messageBundle;
    private final HttpRequestUtils requestUtils;

    @Override
    public UserProfileResponse getLoginProfile() {

        String loginId = requestUtils.getCurrentLoginId();

        Admin admin = adminRepo.findById(loginId)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCodeConstants.ERR_ADM001, loginId));

        return UserProfileResponse.of(admin);
    }

}