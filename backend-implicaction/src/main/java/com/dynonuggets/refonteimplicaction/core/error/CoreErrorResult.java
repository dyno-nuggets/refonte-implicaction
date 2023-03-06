package com.dynonuggets.refonteimplicaction.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.*;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CoreErrorResult implements BaseErrorResult {
    USER_NOT_FOUND(NOT_FOUND, USER_NOT_FOUND_MESSAGE),
    USER_ID_NOT_FOUND(NOT_FOUND, USER_ID_NOT_FOUND_MESSAGE),
    USERNAME_NOT_FOUND(NOT_FOUND, USERNAME_NOT_FOUND_MESSAGE),
    USERNAME_ALREADY_EXISTS(CONFLICT, USERNAME_ALREADY_EXISTS_MESSAGE),
    EMAIL_ALREADY_EXISTS(CONFLICT, EMAIL_ALREADY_EXISTS_MESSAGE),
    ACTIVATION_KEY_NOT_FOUND(NOT_FOUND, ACTIVATION_KEY_NOT_FOUND_MESSAGE),
    OPERATION_NOT_PERMITTED(FORBIDDEN, OPERATION_NOT_PERMITTED_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
