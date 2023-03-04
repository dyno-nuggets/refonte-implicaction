package com.dynonuggets.refonteimplicaction.exception;

/**
 * @deprecated depuis la v.2003 cet exception ne doit plus être utilisée. Il faut utiliser une exception
 */
@Deprecated(since = "v.2023", forRemoval = true)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}
