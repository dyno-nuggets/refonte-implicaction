package com.dynonuggets.refonteimplicaction.job.jobapplication.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class JobApplicationUris {
    public static final String APPLY_BASE_URI = "/api/applies";
    public static final String GET_APPLY_URI = "/{applyId}";
}
