package com.dynonuggets.refonteimplicaction.auth.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class AuthenticationException extends ImplicactionException {
    public AuthenticationException(AuthErrorResult errorResult, String value) {
        super(errorResult, value);
    }

    public AuthenticationException(AuthErrorResult errorResult) {
        super(errorResult);
    }
}
