package com.dynonuggets.refonteimplicaction.community.relation.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.API_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RelationUris {
    public static final String RELATION_BASE_URI = API_BASE_URI + "/relations";
    public static final String GET_ALL_COMMUNITY = "/community";
    public static final String GET_ALL_RELATIONS_URI = "/{username}";
    public static final String GET_ALL_RELATIONS_REQUESTS_SENT_URI = "/{username}/sent";
    public static final String GET_ALL_RELATIONS_REQUESTS_RECEIVED_URI = "/{username}/received";
    public static final String REQUEST_RELATION = "/request/{receiverName}";
    public static final String REMOVE_RELATION = "/{relationId}";
    public static final String CONFIRM_RELATION = "/{relationId}/confirm";
}
