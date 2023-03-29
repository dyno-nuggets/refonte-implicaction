package com.dynonuggets.refonteimplicaction.core.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApiUrls {
    public static final String PUBLIC_BASE_URI = "/api/public";
    public static final String POSTS_BASE_URI = "/api/posts";
    public static final String GET_POST_URI = "/{postId}";
    public static final String GET_POST_COMMENTS_URI = "/{postId}/comments";
    public static final String GET_LATEST_POSTS_URI = "/latest/{postsCount}";

    // COMMENTS
    public static final String COMMENTS_BASE_URI = "/api/comments";
    public static final String GET_COMMENT_URI = "/{commentId}";

    // VOTES
    public static final String VOTE_BASE_URI = "/api/votes";
}
