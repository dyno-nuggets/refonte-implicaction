package com.dynonuggets.refonteimplicaction.utils;

public class ApiUrls {

    // COMPANIES
    public static final String COMPANIES_BASE_URI = "/api/companies";

    //Experience
    public static final String EXPERIENCES_BASE_URI = "/api/experiences";
    public static final String DELETE_EXPERIENCES_URI = "/{experienceId}";

    //Training
    public static final String TRAINING_BASE_URI = "/api/trainings";
    public static final String DELETE_TRAINING_URI = "/{trainingId}";

    // JOB-POSTINGS
    public static final String JOBS_BASE_URI = "/api/job-postings";
    public static final String GET_JOB_URI = "/{jobId}";
    public static final String DELETE_JOB_URI = "/{jobId}";
    public static final String ARCHIVE_JOB_URI = "/{jobId}/archive";
    public static final String ARCHIVE_JOBS_URI = "/archive";
    public static final String GET_PENDING_JOB_URI = "/pending";
    public static final String VALIDATE_JOB_URI = "{jobId}/validate";
    public static final String VALIDATED_JOBS = "/validated";
    public static final String GET_LATEST_JOBS_URI = "/latest/{jobsCount}";

    // POSTS
    public static final String POSTS_BASE_URI = "/api/posts";
    public static final String GET_POST_URI = "/{postId}";
    public static final String GET_POST_COMMENTS_URI = "/{postId}/comments";
    public static final String GET_LATEST_POSTS_URI = "/latest/{postsCount}";

    // GROUPS
    public static final String GROUPS_BASE_URI = "/api/groups";
    public static final String GET_ALL_BY_TOP_POSTING_URI = "/top-posting";
    public static final String CREATE_NO_IMAGE = "/no-image";
    public static final String GET_PENDING_GROUP_URI = "/pending";
    public static final String VALIDATE_GROUP_URI = "{groupName}/validate";
    public static final String GET_VALIDATED_GROUPS_URI = "/validated";
    public static final String SUBSCRIBE_GROUP = "/{groupName}/subscribe";

    // COMMENTS
    public static final String COMMENTS_BASE_URI = "/api/comments";
    public static final String GET_COMMENT_URI = "/{commentId}";

    // VOTES
    public static final String VOTE_BASE_URI = "/api/votes";

    // USERS
    public static final String USER_BASE_URI = "/api/users";
    public static final String GET_USER_URI = "/{userId}";
    public static final String GET_FRIEND_URI = "/{userId}/friends";
    public static final String GET_FRIEND_REQUESTS_SENT_URI = "/friends/sent";
    public static final String GET_FRIEND_REQUESTS_RECEIVED_URI = "/friends/received";
    public static final String GET_PENDING_USER_URI = "/pending";
    public static final String GET_COMMUNITY_URI = "/community";
    public static final String SET_USER_IMAGE = "/image";
    public static final String GET_USER_GROUPS_URI = "/{userId}/groups";

    // FILES
    public static final String FILE_BASE_URI = "/api/files";
    public static final String GET_FILE_BY_KEY = "/{objectKey}";

    // APPLICATION
    public static final String APPLY_BASE_URI = "/api/applies";
    public static final String GET_APPLY_URI = "/{applyId}";

    private ApiUrls() {
        // empêche la construction d'un objet ApiUrls
    }
}
