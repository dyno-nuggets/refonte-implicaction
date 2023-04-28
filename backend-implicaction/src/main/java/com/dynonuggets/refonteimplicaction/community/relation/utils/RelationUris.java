package com.dynonuggets.refonteimplicaction.community.relation.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.API_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RelationUris {
    public static final String RELATION_BASE_URI = API_BASE_URI + "/relations";
    public static final String REMOVE_RELATION = "/{relationId}";
    public static final String CONFIRM_RELATION = "/{relationId}/confirm";
}
