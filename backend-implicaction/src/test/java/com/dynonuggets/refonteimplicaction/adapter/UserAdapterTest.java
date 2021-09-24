package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.JobSeeker;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterTest {

    User user;
    JobSeeker jobSeeker;
    List<WorkExperience> experiences;
    List<Training> trainings;
    List<WorkExperienceDto> expectedExperienceDtos;
    List<TrainingDto> expectedTrainingDtos;
    UserAdapter userAdapter;
    WorkExperienceAdapter workExperienceAdapter;
    TrainingAdapter trainingAdapter;

    @BeforeEach
    public void setUp() {
        experiences = new ArrayList<>();

        experiences.add(new WorkExperience(1L, user, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description", "companyName"));

        trainings = new ArrayList<>();

        trainings.add(new Training(2L, user, "label", LocalDate.now(), "supdevinci"));

        user = User.builder()
                .id(10L)
                .username("username")
                .email("test@test.fr")
                .url("http/404")
                .registeredAt(Instant.now())
                .phoneNumber("0000000")
                .birthday(LocalDate.now())
                .firstname("firstname")
                .lastname("lastname")
                .build();

        jobSeeker = JobSeeker.builder()
                .user(user)
                .experiences(experiences)
                .trainings(trainings)
                .build();

        trainingAdapter = new TrainingAdapter();
        workExperienceAdapter = new WorkExperienceAdapter();
        userAdapter = new UserAdapter(workExperienceAdapter, trainingAdapter, new CompanyAdapter());
    }

    @Test
    void toDtoTest() {
        UserDto userDto = userAdapter.toDto(user);

        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getUrl()).isEqualTo(user.getUrl());
        assertThat(userDto.getRegisteredAt()).isEqualTo(user.getRegisteredAt());
        assertThat(userDto.getRegisteredAt()).isEqualTo(user.getRegisteredAt());
        assertThat(userDto.getBirthday()).isEqualTo(user.getBirthday());
    }

    @Test
    void ToDtoJobSeekerTest() {
        final UserDto userDto = userAdapter.toDto(jobSeeker);
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getUrl()).isEqualTo(user.getUrl());
        assertThat(userDto.getRegisteredAt()).isEqualTo(user.getRegisteredAt());
        assertThat(userDto.getRegisteredAt()).isEqualTo(user.getRegisteredAt());
        assertThat(userDto.getBirthday()).isEqualTo(user.getBirthday());
    }
}
