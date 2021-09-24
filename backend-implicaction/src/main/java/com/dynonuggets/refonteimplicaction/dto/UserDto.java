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
    private String firstname;
    private String lastname;
    private String email;
    private String url;
    private Integer status;
    private String hobbies;
    private List<TrainingDto> trainings;
    private List<WorkExperienceDto> experiences;
    private String purpose;
    private Instant registeredAt;
    private String presentation;
    private String contribution;
    private boolean registered;
    private LocalDate birthday;
    private String phoneNumber;
    private String activationKey;
    private String expectation;
    private boolean active;
    private List<String> roles;
    private CompanyDto company;
    private RelationTypeEnum relationTypeOfCurrentUser;
}
