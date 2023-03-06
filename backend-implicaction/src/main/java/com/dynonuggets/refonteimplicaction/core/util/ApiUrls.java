package com.dynonuggets.refonteimplicaction.core.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApiUrls {

    // COMPANIES
    public static final String COMPANIES_BASE_URI = "/api/companies";

    // EXPERIENCES
    public static final String EXPERIENCES_BASE_URI = "/api/experiences";
    public static final String DELETE_EXPERIENCES_URI = "/{experienceId}";

    // TRAINING
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

    // COMMENTS
    public static final String COMMENTS_BASE_URI = "/api/comments";
    public static final String GET_COMMENT_URI = "/{commentId}";

    // VOTES
    public static final String VOTE_BASE_URI = "/api/votes";

    // FILES
    public static final String FILE_BASE_URI = "/api/files";
    public static final String GET_FILE_BY_KEY = "/{objectKey}";

    // APPLICATION
    public static final String APPLY_BASE_URI = "/api/applies";
    public static final String GET_APPLY_URI = "/{applyId}";
}
