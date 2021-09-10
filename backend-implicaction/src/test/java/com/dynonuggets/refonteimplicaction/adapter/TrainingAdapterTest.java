package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
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

class TrainingAdapterTest {
    User user;
    List<WorkExperience> experiences;
    List<Training> trainings;
    List<WorkExperienceDto> expectedExperienceDtos;
    List<TrainingDto> expectedTrainingDtos;
    UserAdapter userAdapter;
    WorkExperienceAdapter workExperienceAdapter;
    TrainingAdapter trainingAdapter;
    WorkExperience experience;
    Training training;
    RelationAdapter relationAdapter;

    @BeforeEach
    public void setUp() {
        experiences = new ArrayList<>();
        experience = new WorkExperience(1L, user, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description");
        experiences.add(experience);

        trainings = new ArrayList<>();
        training = new Training(2L, user, "label", LocalDate.now());
        trainings.add(training);

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
                .trainings(trainings)
                .build();

        trainingAdapter = new TrainingAdapter();
        workExperienceAdapter = new WorkExperienceAdapter();
        userAdapter = new UserAdapter(workExperienceAdapter, trainingAdapter);
        relationAdapter = new RelationAdapter(userAdapter);

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
    public void trainingToDtoWithoutUser() {
        TrainingDto trainingDto = trainingAdapter.toDtoWithoutUser(training);
        assertThat(trainingDto.getId()).isEqualTo(training.getId());
        assertThat(trainingDto.getDate()).isEqualTo(training.getDate());
        assertThat(trainingDto.getLabel()).isEqualTo(training.getLabel());
    }
}
