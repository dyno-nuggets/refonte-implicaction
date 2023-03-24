package com.dynonuggets.refonteimplicaction.community.profile.dto;

import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ProfileDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String avatar;
    private LocalDate birthday;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String phoneNumber;
    private List<WorkExperienceDto> experiences;
    private List<TrainingDto> trainings;
    private List<GroupDto> groups;
}
