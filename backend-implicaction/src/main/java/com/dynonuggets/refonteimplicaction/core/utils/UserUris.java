package com.dynonuggets.refonteimplicaction.core.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserUris {
    public static final String USER_BASE_URI = "/api/users";
    public static final String GET_PENDING_USER_URI = "/pending";
    public static final String ENABLE_USER = "/{username}/enable";
}
