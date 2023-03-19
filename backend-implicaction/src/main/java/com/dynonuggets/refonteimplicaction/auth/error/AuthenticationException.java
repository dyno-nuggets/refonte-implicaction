package com.dynonuggets.refonteimplicaction.auth.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class AuthenticationException extends ImplicactionException {
    public AuthenticationException(final AuthErrorResult errorResult, final String value) {
        super(errorResult, value);
    }

    public AuthenticationException(final AuthErrorResult errorResult) {
        super(errorResult);
    }
}
