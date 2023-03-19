package com.dynonuggets.refonteimplicaction.exception;

/**
 * @deprecated depuis la v.2003 cet exception ne doit plus être utilisée. Il faut utiliser une exception. L'utilisation de l'exception
 * {@link com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException} est à favoriser
 */
@Deprecated(since = "v.2023")
public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }
}
