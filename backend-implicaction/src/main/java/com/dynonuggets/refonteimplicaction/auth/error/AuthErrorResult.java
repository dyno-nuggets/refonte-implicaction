package com.dynonuggets.refonteimplicaction.auth.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.*;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AuthErrorResult implements BaseErrorResult {
    USER_NOT_FOUND(NOT_FOUND, USER_NOT_FOUND_MESSAGE),
    USERNAME_NOT_FOUND(NOT_FOUND, USERNAME_NOT_FOUND_MESSAGE),
    USERNAME_ALREADY_EXISTS(CONFLICT, USERNAME_ALREADY_EXISTS_MESSAGE),
    EMAIL_ALREADY_EXISTS(CONFLICT, EMAIL_ALREADY_EXISTS_MESSAGE),
    ACTIVATION_KEY_NOT_FOUND(NOT_FOUND, ACTIVATION_KEY_NOT_FOUND_MESSAGE),
    REFRESH_TOKEN_EXPIRED(FORBIDDEN, REFRESH_TOKEN_EXPIRED_MESSAGE),
    USER_ALREADY_ACTIVATED(BAD_REQUEST, USER_ALREADY_ACTIVATED_MESSAGE),
    USER_IS_NOT_ACTIVATED(UNAUTHORIZED, USER_IS_NOT_ACTIVATED_MESSAGE),

    PUBLIC_KEY_ERROR(INTERNAL_SERVER_ERROR, PUBLIC_KEY_ERROR_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
