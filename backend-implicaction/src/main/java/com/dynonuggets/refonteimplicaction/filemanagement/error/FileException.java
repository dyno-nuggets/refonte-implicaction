package com.dynonuggets.refonteimplicaction.filemanagement.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class FileException extends ImplicactionException {
    public FileException(final BaseErrorResult errorResult, final String value) {
        super(errorResult, value);
    }
}
