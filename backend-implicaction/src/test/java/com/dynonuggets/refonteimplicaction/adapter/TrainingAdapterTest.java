package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingAdapterTest {
    User user;
    List<WorkExperience> experiences;
    List<Training> trainings;

    TrainingAdapter trainingAdapter = new TrainingAdapter();

    WorkExperience experience;


    Training training;

    @BeforeEach
    public void setUp() {
        experiences = new ArrayList<>();
        experience = new WorkExperience(1L, user, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description", "companyName");
        experiences.add(experience);

        trainings = new ArrayList<>();
        training = new Training(2L, user, "label", LocalDate.now(), "supdevinci");
        trainings.add(training);

        user = User.builder()
                .id(10L)
                .username("username")
                .email("test@test.fr")
                .url("http/404")
                .phoneNumber("0000000")
                .birthday(LocalDate.now())
                .build();
    }

    @Test
    void trainingToDtoWithoutUser() {
        TrainingDto trainingDto = trainingAdapter.toDtoWithoutUser(training);
        assertThat(trainingDto.getId()).isEqualTo(training.getId());
        assertThat(trainingDto.getDate()).isEqualTo(training.getDate());
        assertThat(trainingDto.getLabel()).isEqualTo(training.getLabel());
        assertThat(trainingDto.getSchool()).isEqualTo(training.getSchool());

    }
}
