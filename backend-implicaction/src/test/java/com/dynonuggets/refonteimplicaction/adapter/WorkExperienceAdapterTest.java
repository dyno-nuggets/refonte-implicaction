package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkExperienceAdapterTest {
    User user;
    List<WorkExperience> experiences;

    @Mock
    WorkExperienceAdapter workExperienceAdapter;

    @Mock
    WorkExperience experience;

    @Mock
    TrainingAdapter trainingAdapter;

    @Mock
    CompanyAdapter companyAdapter;

    @InjectMocks
    UserAdapter userAdapter;

    @InjectMocks
    RelationAdapter relationAdapter;

    @BeforeEach
    public void setUp() {
        experiences = new ArrayList<>();
        experience = new WorkExperience(1L, user, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description", "companyName");
        experiences.add(experience);
        experience.setUser(user);

        workExperienceAdapter = new WorkExperienceAdapter();
    }

    @Test
    void workExperienceToDtoWithoutUser() {
        WorkExperienceDto workExperienceDto = workExperienceAdapter.toDtoWithoutUser(experience);
        assertThat(workExperienceDto.getId()).isEqualTo(experience.getId());
        assertThat(workExperienceDto.getStartedAt()).isEqualTo(workExperienceDto.getStartedAt());
        assertThat(workExperienceDto.getFinishedAt()).isEqualTo(workExperienceDto.getFinishedAt());
        assertThat(workExperienceDto.getLabel()).isEqualTo(workExperienceDto.getLabel());
        assertThat(workExperienceDto.getDescription()).isEqualTo(workExperienceDto.getDescription());
    }
}
