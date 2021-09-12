package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterTest {

    User user;
    List<WorkExperience> experiences;
    List<Training> training;
    List<WorkExperienceDto> expectedExperienceDtos;
    List<TrainingDto> expectedTrainingDtos;
    UserAdapter userAdapter;
    WorkExperienceAdapter workExperienceAdapter;
    TrainingAdapter trainingAdapter;

    @BeforeEach
    public void setUp() {
        experiences = new ArrayList<>();

        experiences.add(new WorkExperience(1L, user, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description"));

        training = new ArrayList<>();

        training.add(new Training(2L, user, "label", LocalDate.now()));

        user = User.builder()
                .id(10L)
                .username("username")
                .nicename("nicename")
                .email("test@test.fr")
                .url("http/404")
                .registered(Instant.now())
                .status(0)
                .displayName("dispayname")
                .phoneNumber("0000000")
                .birthday(LocalDate.now())
                .hobbies("hobbies")
                .experiences(experiences)
                .trainings(training)
                .purpose("purpose")
                .presentation("presentation")
                .expectation("expectation")
                .contribution("contribution")
                .firstName("firstname")
                .lastName("lastname")
                .build();

        trainingAdapter = new TrainingAdapter();
        workExperienceAdapter = new WorkExperienceAdapter();
        userAdapter = new UserAdapter(workExperienceAdapter, trainingAdapter);

        expectedExperienceDtos = user.getExperiences()
                .stream()
                .map(workExperienceAdapter::toDtoWithoutUser)
                .collect(Collectors.toList());

        expectedTrainingDtos = user.getTrainings()
                .stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(Collectors.toList());
    }

    @Test
    void toDtoTest() {
        UserDto userDto = userAdapter.toDto(user);

        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getNicename()).isEqualTo(user.getNicename());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getUrl()).isEqualTo(user.getUrl());
        assertThat(userDto.getRegistered()).isEqualTo(user.getRegistered());
        assertThat(userDto.getStatus()).isEqualTo(user.getStatus());
        assertThat(userDto.getDisplayName()).isEqualTo(user.getDisplayName());
        assertThat(userDto.getRegistered()).isEqualTo(user.getRegistered());
        assertThat(userDto.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(userDto.getHobbies()).isEqualTo(user.getHobbies());
        assertThat(userDto.getExperiences()).containsAll(expectedExperienceDtos);
        assertThat(userDto.getTrainings()).containsAll(expectedTrainingDtos);
        assertThat(userDto.getPurpose()).isEqualTo(user.getPurpose());
        assertThat(userDto.getPresentation()).isEqualTo(user.getPresentation());
        assertThat(userDto.getExpectation()).isEqualTo(user.getExpectation());
        assertThat(userDto.getContribution()).isEqualTo(user.getContribution());
        assertThat(userDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDto.getLastName()).isEqualTo(user.getLastName());
    }
}
