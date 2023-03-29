package com.dynonuggets.refonteimplicaction.community.workexperience.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class WorkExperienceUris {
    public static final String EXPERIENCES_BASE_URI = "/api/experiences";
    public static final String DELETE_EXPERIENCES_URI = "/{experienceId}";
}
