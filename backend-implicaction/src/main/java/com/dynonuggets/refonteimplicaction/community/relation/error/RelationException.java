package com.dynonuggets.refonteimplicaction.community.relation.error;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;

public class RelationException extends ImplicactionException {
    public RelationException(final RelationErrorResult errorResult) {
        super(errorResult);
    }
}
