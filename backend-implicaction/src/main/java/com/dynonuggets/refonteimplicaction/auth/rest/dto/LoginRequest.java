package com.dynonuggets.refonteimplicaction.auth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthConstants.*;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
public class LoginRequest {
    @NotBlank
    @Pattern(regexp = USERNAME_VALIDATION_PATTERN)
    private String username;
    @NotBlank
    // TODO: créer une custom Password Constraint https://www.baeldung.com/registration-password-strength-and-rules
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
}
