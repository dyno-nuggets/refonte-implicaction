package com.dynonuggets.refonteimplicaction.community.workexperience.adapter;

import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceAdapter {

    public WorkExperienceDto toDto(final WorkExperienceModel workExperience) {

        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .startedAt(workExperience.getStartedAt())
                .finishedAt(workExperience.getFinishedAt())
                .label(workExperience.getLabel())
                .description(workExperience.getDescription())
                .companyName(workExperience.getCompanyName())
                .build();
    }

    public WorkExperienceModel toModel(final WorkExperienceDto workExperienceDto) {
        return WorkExperienceModel.builder()
                .id(workExperienceDto.getId())
                .label(workExperienceDto.getLabel())
                .startedAt(workExperienceDto.getStartedAt())
                .finishedAt(workExperienceDto.getFinishedAt())
                .description(workExperienceDto.getDescription())
                .companyName(workExperienceDto.getCompanyName())
                .build();
    }
}
