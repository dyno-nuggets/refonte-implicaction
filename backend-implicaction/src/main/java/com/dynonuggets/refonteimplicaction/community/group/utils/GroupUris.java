package com.dynonuggets.refonteimplicaction.community.group.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GroupUris {
    public static final String GROUPS_BASE_URI = "/api/groups";
    public static final String CREATE_NO_IMAGE = "/no-image";
    public static final String GET_PENDING_GROUP_URI = "/pending";
    public static final String ENABLE_GROUP_URI = "/{groupId}/enable";
    public static final String GET_VALIDATED_GROUPS_URI = "/enabled";
    public static final String SUBSCRIBE_GROUP = "/{groupId}/subscribe";
}
