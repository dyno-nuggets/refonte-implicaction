package com.dynonuggets.refonteimplicaction.community.profile.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class ProfileUpdateRequest {
    @NotNull
    private String username;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    private String email;
    private LocalDate birthday;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String phoneNumber;
}
