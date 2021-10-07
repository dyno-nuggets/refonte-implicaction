package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthenticationResponseDto {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private UserDto currentUser;
}
