package com.dynonuggets.refonteimplicaction.core.dto;

import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String email;
    private Instant registeredAt;
    private String activationKey;
    private Boolean enabled;
    private Boolean emailVerified;
    private Set<RoleEnum> roles;
}
