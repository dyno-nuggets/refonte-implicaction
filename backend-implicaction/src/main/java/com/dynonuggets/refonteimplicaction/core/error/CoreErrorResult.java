package com.dynonuggets.refonteimplicaction.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.OPERATION_NOT_PERMITTED_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@AllArgsConstructor
public enum CoreErrorResult implements BaseErrorResult {
    OPERATION_NOT_PERMITTED(FORBIDDEN, OPERATION_NOT_PERMITTED_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
