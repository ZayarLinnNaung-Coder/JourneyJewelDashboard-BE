package root.development.tms.features.profile.service;

import root.development.tms.features.profile.domain.response.UserProfileResponse;

public interface ProfileService {
    UserProfileResponse getLoginProfile();
}
