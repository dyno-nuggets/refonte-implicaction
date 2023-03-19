package com.dynonuggets.refonteimplicaction.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Instant registeredAt;
    private String activationKey;
    private boolean active;
    private boolean emailVerified;
    private List<String> roles;
}
