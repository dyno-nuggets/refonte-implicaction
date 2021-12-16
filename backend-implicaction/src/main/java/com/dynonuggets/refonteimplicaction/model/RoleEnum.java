package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(1L, "ROLE_USER"),
    ADMIN(2L, "ROLE_ADMIN"),
    JOB_SEEKER(3L, "ROLE_JOB_SEEKER"),
    RECRUITER(4L, "ROLE_RECRUITER"),
    PREMIUM(5L, "ROLE_PREMIUM");

    private final Long id;
    private final String longName;

}
