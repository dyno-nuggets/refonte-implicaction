package com.dynonuggets.refonteimplicaction.exception;

/**
 * @deprecated Depuis la v.2003 cette exception ne doit plus être utilisée. Il faut utiliser une exception. L’utilisation de l’exception
 * {@link com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException} est à favoriser
 */
@Deprecated(since = "v.2023", forRemoval = true)
public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }
}
