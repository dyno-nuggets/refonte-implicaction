package com.dynonuggets.refonteimplicaction.community.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RelationUris {
    public static final String RELATION_BASE_URI = "/api/relations";
    public static final String GET_ALL_RELATIONS_URI = "/{userId}";
    public static final String GET_ALL_RELATIONS_REQUESTS_SENT_URI = "/sent";
    public static final String GET_ALL_RELATIONS_REQUESTS_RECEIVED_URI = "/received";
    public static final String REQUEST_RELATION = "/request/{receiverId}";
    public static final String DELETE_RELATION = "/{senderId}/decline";
    public static final String CANCEL_RELATION_REQUEST = "/{userId}/cancel";
    public static final String CONFIRM_RELATION = "/{senderId}/confirm";
}
