package com.dynonuggets.refonteimplicaction.auth.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthUris {
    public static final String AUTH_BASE_URI = "/api/auth";
    public static final String AUTH_SIGNUP_URI = "/signup";
    public static final String AUTH_LOGIN_URI = "/login";
    public static final String AUTH_ACCOUNT_VERIFICATION_URI = "/accountVerification/{activationKey}";
    public static final String AUTH_REFRESH_TOKENS_URI = "/refresh/token";
    public static final String AUTH_LOGOUT_URI = "/logout";
}
