package com.dynonuggets.refonteimplicaction.forum.topic.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.PUBLIC_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TopicUris {
    public static final String TOPIC_BASE_URI = "/api/forums/topics";
    public static final String PUBLIC_TOPICS_BASE_URI = PUBLIC_BASE_URI + "/forums/topics";
    public static final String GET_LATEST_TOPICS = "/latest";
    public static final String GET_TOPIC_URI = "/{topicId}";
    public static final String GET_RESPONSE_FROM_TOPIC_URI = "/{topicId}/responses";

    public static final String DELETE_TOPIC_URI = "/{topicId}";
}
