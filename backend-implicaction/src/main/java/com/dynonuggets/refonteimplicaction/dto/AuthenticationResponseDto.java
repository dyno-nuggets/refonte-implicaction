package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthenticationResponseDto {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
    private Long userId;
    private Set<String> roles;
}
