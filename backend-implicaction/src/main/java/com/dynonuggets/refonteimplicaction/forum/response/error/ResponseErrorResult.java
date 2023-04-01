package com.dynonuggets.refonteimplicaction.forum.response.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.forum.response.utils.ResponseMessages.RESPONSE_NOT_FOUND_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ResponseErrorResult implements BaseErrorResult {

    RESPONSE_NOT_FOUND(NOT_FOUND, RESPONSE_NOT_FOUND_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
