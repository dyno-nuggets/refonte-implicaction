package com.dynonuggets.refonteimplicaction.user.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.user.util.UserMessages.USERNAME_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.user.util.UserMessages.USER_ID_NOT_FOUND_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum UserErrorResult implements BaseErrorResult {
    USER_ID_NOT_FOUND(NOT_FOUND, USER_ID_NOT_FOUND_MESSAGE),
    USERNAME_NOT_FOUND(NOT_FOUND, USERNAME_NOT_FOUND_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
