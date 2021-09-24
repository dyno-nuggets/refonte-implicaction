package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDto {
    private UserDto user;
    private String hobbies;
    private List<TrainingDto> trainings;
    private List<WorkExperienceDto> experiences;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
}
