package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.core.domain.model.User;
import com.dynonuggets.refonteimplicaction.community.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.rest.dto.TrainingDto;
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
        experience = new WorkExperience(1L, null, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description", "companyName");
        experiences.add(experience);

        trainings = new ArrayList<>();
        training = new Training(2L, null, "label", LocalDate.now(), "supdevinci");
        trainings.add(training);

        user = User.builder()
                .id(10L)
                .username("username")
                .email("test@test.fr")
                .build();
    }

    @Test
    void trainingToDtoWithoutUser() {
        final TrainingDto trainingDto = trainingAdapter.toDto(training);
        assertThat(trainingDto.getId()).isEqualTo(training.getId());
        assertThat(trainingDto.getDate()).isEqualTo(training.getDate());
        assertThat(trainingDto.getLabel()).isEqualTo(training.getLabel());
        assertThat(trainingDto.getSchool()).isEqualTo(training.getSchool());

    }
}
