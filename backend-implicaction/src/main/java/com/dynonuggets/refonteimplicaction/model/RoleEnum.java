package com.dynonuggets.refonteimplicaction.model;

import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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

    public static RoleEnum byLongName(final String longName) {
        return Arrays.stream(RoleEnum.values())
                .filter(roleEnum -> roleEnum.getLongName().equals(longName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("role: %s not found.", longName)));
    }

}
