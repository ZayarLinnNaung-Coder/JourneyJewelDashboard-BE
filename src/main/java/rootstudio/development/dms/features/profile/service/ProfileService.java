package rootstudio.development.dms.features.profile.service;

import rootstudio.development.dms.features.profile.domain.response.UserProfileResponse;

public interface ProfileService {
    UserProfileResponse getLoginProfile();
}
