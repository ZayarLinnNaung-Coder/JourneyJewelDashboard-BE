package root.development.tms.features.profile.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import root.development.tms.features.profile.domain.response.UserProfileResponse;
import root.development.tms.features.profile.service.ProfileService;
import root.development.tms.global.constants.ErrorCodeConstants;
import root.development.tms.global.document.Admin;
import root.development.tms.global.exception.model.AccountNotFoundException;
import root.development.tms.global.repo.AdminRepo;
import root.development.tms.global.utils.HttpRequestUtils;
import root.development.tms.global.utils.MessageBundle;

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