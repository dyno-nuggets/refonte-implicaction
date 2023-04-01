package com.dynonuggets.refonteimplicaction.forum.commons.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class ForumException extends ImplicactionException {
    public ForumException(final ForumErrorResult errorResult, final String value) {
        super(errorResult, value);
    }

    public ForumException(final ForumErrorResult errorResult) {
        super(errorResult);
    }
}
