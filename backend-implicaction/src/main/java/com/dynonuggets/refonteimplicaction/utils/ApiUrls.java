package com.dynonuggets.refonteimplicaction.utils;

public class ApiUrls {

    // JOB-POSTINGS
    public static final String JOB_BASE_URI = "/api/job-postings";
    public static final String GET_JOB_URI = "/{jobId}";
    public static final String DELETE_JOB_URI = "/{jobId}";

    // POSTS
    public static final String POST_BASE_URI = "/api/posts";
    public static final String GET_POST_URI = "/{postId}";

    // SUBREDDIT
    public static final String SUBREDDIT_BASE_URI = "/api/sub-reddit";

    private ApiUrls() {
        // empêche la construction d'un objet ApiUrls
    }
}
