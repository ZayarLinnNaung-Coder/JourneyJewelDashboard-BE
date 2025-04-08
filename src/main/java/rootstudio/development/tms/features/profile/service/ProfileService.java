package rootstudio.development.tms.features.profile.service;

import rootstudio.development.tms.features.profile.domain.response.UserProfileResponse;

public interface ProfileService {
    UserProfileResponse getLoginProfile();
}
