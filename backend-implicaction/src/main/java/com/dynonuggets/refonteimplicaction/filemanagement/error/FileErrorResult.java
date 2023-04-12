package com.dynonuggets.refonteimplicaction.filemanagement.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileMessages.FILE_SIZE_TOO_LARGE_MESSAGE;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileMessages.UNAUTHORIZED_CONTENT_TYPE_MESSAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum FileErrorResult implements BaseErrorResult {

    FILE_IS_TOO_LARGE(BAD_REQUEST, FILE_SIZE_TOO_LARGE_MESSAGE),
    UNAUTHORIZED_CONTENT_TYPE(BAD_REQUEST, UNAUTHORIZED_CONTENT_TYPE_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
