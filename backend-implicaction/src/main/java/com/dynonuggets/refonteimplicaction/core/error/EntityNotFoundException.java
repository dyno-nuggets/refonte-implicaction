package com.dynonuggets.refonteimplicaction.core.error;

public class EntityNotFoundException extends ImplicactionException {
    public EntityNotFoundException(final BaseErrorResult errorResult, final String value) {
        super(errorResult, value);
    }

    public EntityNotFoundException(final BaseErrorResult errorResult) {
        super(errorResult);
    }
}
