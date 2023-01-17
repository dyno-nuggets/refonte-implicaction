package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {
    @NotBlank
    private String refreshToken;
    private String username;
}
