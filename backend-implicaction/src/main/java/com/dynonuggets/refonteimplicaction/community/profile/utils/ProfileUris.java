package com.dynonuggets.refonteimplicaction.community.profile.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.API_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProfileUris {
    public static final String PROFILES_BASE_URI = API_BASE_URI + "/profiles";
    public static final String GET_PROFILE_BY_USERNAME = "/{username}";
}
