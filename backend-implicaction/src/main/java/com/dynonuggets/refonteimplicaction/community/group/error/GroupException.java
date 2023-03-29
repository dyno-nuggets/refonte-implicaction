package com.dynonuggets.refonteimplicaction.community.group.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class GroupException extends ImplicactionException {
    public GroupException(final GroupErrorResult errorResult, final String value) {
        super(errorResult, value);
    }
}
