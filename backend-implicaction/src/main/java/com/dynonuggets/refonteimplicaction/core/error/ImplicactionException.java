package com.dynonuggets.refonteimplicaction.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Getter
@AllArgsConstructor
public abstract class ImplicactionException extends RuntimeException {

    private final BaseErrorResult errorResult;
    private final String value;

    public ImplicactionException(final BaseErrorResult errorResult) {
        this(errorResult, null);
    }

    @Override
    public String getMessage() {
        final String message = errorResult.getMessage();
        if (isNotEmpty(value) && isNotEmpty(message) && message.contains("%s")) {
            return format(message, value);
        }
        return message;
    }
}
