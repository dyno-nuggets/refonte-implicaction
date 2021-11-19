package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(1L, "USER", "ROLE_USER"),
    ADMIN(2L, "ADMIN", "ROLE_ADMIN"),
    JOB_SEEKER(3L, "JOB_SEEKER", "ROLE_JOB_SEEKER"),
    RECRUITER(4L, "RECRUITER", "ROLE_RECRUITER"),
    PREMIUM(5L, "PREMIUM", "ROLE_PREMIUM");

    private Long id;
    private String name;
    private String longName;

}
