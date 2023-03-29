package com.dynonuggets.refonteimplicaction.user.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    RECRUITER("ROLE_RECRUITER"),
    PREMIUM("ROLE_PREMIUM");

    private final String longName;

}
