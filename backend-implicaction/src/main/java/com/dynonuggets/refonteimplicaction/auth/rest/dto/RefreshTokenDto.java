package com.dynonuggets.refonteimplicaction.auth.rest.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
public class RefreshTokenDto {
    private String token;
    private Instant creationDate;
}
