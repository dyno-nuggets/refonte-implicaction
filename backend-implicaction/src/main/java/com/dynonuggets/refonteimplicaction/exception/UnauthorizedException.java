package com.dynonuggets.refonteimplicaction.exception;

/**
 * @deprecated Depuis la v.2003 cette exception ne doit plus être utilisée. Il faut utiliser une exception
 */
@Deprecated(since = "v.2023", forRemoval = true)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String message) {
        super(message);
    }
}
