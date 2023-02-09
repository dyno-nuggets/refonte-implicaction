package com.dynonuggets.refonteimplicaction.auth.rest.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthConstants.*;
import static lombok.AccessLevel.PRIVATE;

@Data
@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class RegisterRequest {
    @NotBlank
    @Pattern(regexp = USERNAME_VALIDATION_PATTERN)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    // TODO: créer une custom Password Constraint https://www.baeldung.com/registration-password-strength-and-rules
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    // TODO: à déplacer dans la partie profil
    private String firstname;

    // TODO: à déplacer dans la partie profil
    private String lastname;
}
