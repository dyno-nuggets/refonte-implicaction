package com.dynonuggets.refonteimplicaction.community.profile.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileUpdateRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String phoneNumber;
}
