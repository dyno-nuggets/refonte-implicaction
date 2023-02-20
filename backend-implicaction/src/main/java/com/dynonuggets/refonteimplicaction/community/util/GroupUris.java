package com.dynonuggets.refonteimplicaction.community.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GroupUris {
    public static final String GROUPS_BASE_URI = "/api/groups";
    public static final String GET_ALL_BY_TOP_POSTING_URI = "/top-posting";
    public static final String CREATE_NO_IMAGE = "/no-image";
    public static final String GET_PENDING_GROUP_URI = "/pending";
    public static final String VALIDATE_GROUP_URI = "{groupName}/validate";
    public static final String GET_VALIDATED_GROUPS_URI = "/validated";
    public static final String SUBSCRIBE_GROUP = "/{groupName}/subscribe";
}
