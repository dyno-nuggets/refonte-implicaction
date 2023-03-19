package com.dynonuggets.refonteimplicaction.auth.dto;

import com.dynonuggets.refonteimplicaction.core.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class LoginResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private UserDto currentUser;
}
