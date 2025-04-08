package rootstudio.development.dms.features.profile.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rootstudio.development.dms.features.profile.domain.response.UserProfileResponse;
import rootstudio.development.dms.features.profile.service.ProfileService;
import rootstudio.development.dms.global.constants.ErrorCodeConstants;
import rootstudio.development.dms.global.document.Admin;
import rootstudio.development.dms.global.exception.model.AccountNotFoundException;
import rootstudio.development.dms.global.repo.AdminRepo;
import rootstudio.development.dms.global.utils.HttpRequestUtils;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.text.MessageFormat;

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