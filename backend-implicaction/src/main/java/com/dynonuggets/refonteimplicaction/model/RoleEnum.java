package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(1L, "USER"),
    ADMIN(2L, "ADMIN"),
    JOB_SEEKER(3L, "JOB_SEEKER"),
    RECRUITER(4L, "RECRUITER");

    private Long id;
    private String label;

}
