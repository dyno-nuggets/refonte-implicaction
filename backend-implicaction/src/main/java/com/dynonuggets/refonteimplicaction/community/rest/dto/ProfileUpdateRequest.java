package com.dynonuggets.refonteimplicaction.community.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileUpdateRequest {
    private String username;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String phoneNumber;
}
