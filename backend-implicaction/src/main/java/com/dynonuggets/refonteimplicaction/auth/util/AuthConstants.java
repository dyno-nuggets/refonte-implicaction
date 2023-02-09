package com.dynonuggets.refonteimplicaction.auth.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthConstants {

    public static final String USERNAME_VALIDATION_PATTERN = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?![_.])$";
    public static final short PASSWORD_MIN_LENGTH = 6;
    public static final short PASSWORD_MAX_LENGTH = 100;
}
