package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String nicename;
    private String email;
    private String url;
    private Instant registered;
    private Integer status;
    private String displayName;
    private String phoneNumber;
    private LocalDate birthday;
    private String hobbies;
    private List<TrainingDto> trainings;
    private List<WorkExperienceDto> experiences;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String firstName;
    private String lastName;

}
