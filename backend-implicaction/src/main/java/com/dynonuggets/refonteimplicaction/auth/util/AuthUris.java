package com.dynonuggets.refonteimplicaction.auth.util;

import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthUris {
    public static final String AUTH_BASE_URI = "/api/auth";
    public static final String AUTH_SIGNUP_URI = "/signup";
    public static final String AUTH_LOGIN_URI = "/login";
    public static final String AUTH_ACCOUNT_VERIFICATION_URI = "/accountVerification/{activationKey}";
    public static final String AUTH_REFRESH_TOKENS_URI = "/refresh/token";
    public static final String AUTH_LOGOUT_URI = "/logout";

    public static List<String> getPublicUris() {
        return of(AUTH_SIGNUP_URI, AUTH_LOGIN_URI, AUTH_ACCOUNT_VERIFICATION_URI, AUTH_REFRESH_TOKENS_URI, AUTH_LOGOUT_URI)
                .map(AUTH_BASE_URI::concat)
                .collect(toList());
    }
}
