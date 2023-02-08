package com.dynonuggets.refonteimplicaction.auth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthConstants.USERNAME_VALIDATION_PATTERN;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    @NotBlank
    @Pattern(regexp = USERNAME_VALIDATION_PATTERN)
    private String username;
}
