package com.dynonuggets.refonteimplicaction.core.error;

public class CoreException extends ImplicactionException {
    public CoreException(final CoreErrorResult errorResult, final String value) {
        super(errorResult, value);
    }

    public CoreException(final CoreErrorResult errorResult) {
        super(errorResult);
    }
}
