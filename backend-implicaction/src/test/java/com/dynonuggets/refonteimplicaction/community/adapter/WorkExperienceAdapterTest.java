package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.dto.WorkExperienceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkExperienceAdapterTest {

    @Mock
    WorkExperienceAdapter workExperienceAdapter;

    WorkExperience experience;
    List<WorkExperience> experiences;

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
