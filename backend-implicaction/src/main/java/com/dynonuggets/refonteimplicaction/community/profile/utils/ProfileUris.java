package com.dynonuggets.refonteimplicaction.community.profile.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProfileUris {
    public static final String PROFILES_BASE_URI = "/api/profiles";
    public static final String GET_PROFILE_BY_USERNAME = "/{username}";
    public static final String POST_PROFILE_AVATAR = "/{username}";
}
