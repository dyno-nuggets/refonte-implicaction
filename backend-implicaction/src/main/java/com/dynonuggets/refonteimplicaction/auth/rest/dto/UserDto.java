package com.dynonuggets.refonteimplicaction.auth.rest.dto;

import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
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
    private LocalDate birthday;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String phoneNumber;
    private Instant registeredAt;
    private Instant activatedAt;
    private String activationKey;
    private Boolean active;
    private String imageUrl;
    private List<String> roles;
    private List<TrainingDto> trainings;
    private List<WorkExperienceDto> experiences;
    private RelationTypeEnum relationTypeOfCurrentUser;
}
