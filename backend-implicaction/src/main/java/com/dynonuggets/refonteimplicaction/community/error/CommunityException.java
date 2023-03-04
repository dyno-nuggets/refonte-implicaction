package com.dynonuggets.refonteimplicaction.community.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class CommunityException extends ImplicactionException {
    public CommunityException(final CommunityErrorResult errorResult, final String value) {
        super(errorResult, value);
    }

    public CommunityException(final CommunityErrorResult errorResult) {
        super(errorResult);
    }
}
