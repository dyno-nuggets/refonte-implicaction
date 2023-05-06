package com.dynonuggets.refonteimplicaction.job.jobposting.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.PUBLIC_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class JobPostingUris {
    public static final String JOBS_BASE_URI = "/api/job-postings";
    public static final String PUBLIC_JOBS_BASE_URI = PUBLIC_BASE_URI + "/job-postings";
    public static final String GET_JOB_URI = "/{jobId}";
    public static final String DELETE_JOB_URI = "/{jobId}";
    public static final String ARCHIVE_JOB_URI = "/{jobId}/archive";
    public static final String ARCHIVE_JOBS_URI = "/archive";
    public static final String GET_PENDING_JOB_URI = "/pending";
    public static final String VALIDATE_JOB_URI = "{jobId}/validate";
    public static final String VALIDATED_JOBS = "/validated";
    public static final String GET_LATEST_JOBS_URI = "/latest";
    public static final String GET_ENABLED_COUNT = "/count";
}
