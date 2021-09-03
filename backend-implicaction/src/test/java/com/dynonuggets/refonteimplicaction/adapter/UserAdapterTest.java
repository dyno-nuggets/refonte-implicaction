package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAdapterTest {

    User user;
    List<WorkExperience> experiences;
    List<Training> training;
    List<WorkExperienceDto> expectedExperiencesDtos;
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
                .dispayName("dispayname")
                .phoneNumber("0000000")
                .birthday(LocalDate.now())
                .hobbies("hobbies")
                .experiences(experiences)
                .trainings(training)
                .build();

        trainingAdapter = new TrainingAdapter();
        workExperienceAdapter = new WorkExperienceAdapter();
        userAdapter = new UserAdapter(workExperienceAdapter, trainingAdapter);

        expectedExperiencesDtos = user.getExperiences()
                .stream()
                .map(workExperienceAdapter::toDto)
                .collect(Collectors.toList());

        expectedTrainingDtos = user.getTrainings()
                .stream()
                .map(trainingAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Test
    public void toDtoTest() {
        UserDto userDto = userAdapter.toDto(user);
        assertThat(userDto.getId().equals(user.getId())).isTrue();
        assertThat(userDto.getUsername().equals(user.getUsername())).isTrue();
        assertThat(userDto.getNicename().equals(user.getNicename())).isTrue();
        assertThat(userDto.getEmail().equals(user.getEmail())).isTrue();
        assertThat(userDto.getUrl().equals(user.getUrl())).isTrue();
        assertThat(userDto.getRegistered().equals(user.getRegistered())).isTrue();
        assertThat(userDto.getStatus().equals(user.getStatus())).isTrue();
        assertThat(userDto.getDispayName().equals(user.getDispayName())).isTrue();
        assertThat(userDto.getRegistered().equals(user.getRegistered())).isTrue();
        assertThat(userDto.getBirthday().equals(user.getBirthday())).isTrue();
        assertThat(userDto.getHobbies().equals(user.getHobbies())).isTrue();
        assertThat(userDto.getExperiences()).containsAll(expectedExperiencesDtos);
        assertThat(userDto.getTrainings()).containsAll(expectedTrainingDtos);
    }
}
