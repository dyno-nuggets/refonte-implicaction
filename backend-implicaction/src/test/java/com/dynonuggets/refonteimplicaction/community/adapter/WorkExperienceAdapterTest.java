package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.auth.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.rest.dto.WorkExperienceDto;
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
        experience = new WorkExperience(1L, null, LocalDate.now().minusDays(10L), LocalDate.now(), "label", "description", "companyName");
        experiences.add(experience);
        experience.setProfile(null);

        workExperienceAdapter = new WorkExperienceAdapter();
    }

    @Test
    void workExperienceToDtoWithoutUser() {
        final WorkExperienceDto workExperienceDto = workExperienceAdapter.toDto(experience);
        assertThat(workExperienceDto.getId()).isEqualTo(experience.getId());
        assertThat(workExperienceDto.getStartedAt()).isEqualTo(workExperienceDto.getStartedAt());
        assertThat(workExperienceDto.getFinishedAt()).isEqualTo(workExperienceDto.getFinishedAt());
        assertThat(workExperienceDto.getLabel()).isEqualTo(workExperienceDto.getLabel());
        assertThat(workExperienceDto.getDescription()).isEqualTo(workExperienceDto.getDescription());
    }
}
