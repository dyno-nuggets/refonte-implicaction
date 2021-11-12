package com.dynonuggets.refonteimplicaction.utils;

public class ApiUrls {

    // JOB-POSTINGS
    public static final String JOBS_BASE_URI = "/api/job-postings";
    public static final String GET_JOB_URI = "/{jobId}";
    public static final String DELETE_JOB_URI = "/{jobId}";

    // POSTS
    public static final String POSTS_BASE_URI = "/api/posts";
    public static final String GET_POST_URI = "/{postId}";
    public static final String GET_POST_COMMENTS_URI = "/{postId}/comments";

    // SUBREDDITS
    public static final String SUBREDDITS_BASE_URI = "/api/sub-reddits";
    public static final String GET_ALL_BY_TOP_POSTING_URI = "/top-posting";

    // COMMENTS
    public static final String COMMENTS_BASE_URI = "/api/comments";
    public static final String GET_COMMENT_URI = "/{commentId}";

    // VOTES
    public static final String VOTE_BASE_URI = "/api/votes";

    // USERS
    public static final String USER_BASE_URI = "/api/users";
    public static final String GET_USER_URI = "/{userId}";
    public static final String GET_FRIEND_URI = "/{userId}/friends";

    // FILES
    public static final String FILE_BASE_URI = "/api/files";

    private ApiUrls() {
        // empêche la construction d'un objet ApiUrls
    }
}
