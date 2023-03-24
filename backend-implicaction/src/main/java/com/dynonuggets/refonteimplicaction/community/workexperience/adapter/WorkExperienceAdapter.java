package com.dynonuggets.refonteimplicaction.community.workexperience.adapter;

import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceAdapter {

    public WorkExperienceDto toDto(final WorkExperience workExperience) {

        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .startedAt(workExperience.getStartedAt())
                .finishedAt(workExperience.getFinishedAt())
                .label(workExperience.getLabel())
                .description(workExperience.getDescription())
                .companyName(workExperience.getCompanyName())
                .build();
    }

    public WorkExperience toModel(final WorkExperienceDto workExperienceDto) {
        return WorkExperience.builder()
                .id(workExperienceDto.getId())
                .label(workExperienceDto.getLabel())
                .startedAt(workExperienceDto.getStartedAt())
                .finishedAt(workExperienceDto.getFinishedAt())
                .description(workExperienceDto.getDescription())
                .companyName(workExperienceDto.getCompanyName())
                .build();
    }
}
