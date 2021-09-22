package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceAdapter {

    public WorkExperienceDto toDtoWithoutUser(WorkExperience workExperience) {

        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .startedAt(workExperience.getStartedAt())
                .finishedAt(workExperience.getFinishedAt())
                .label(workExperience.getLabel())
                .description(workExperience.getDescription())
                .companyName(workExperience.getCompanyName())
                .build();
    }

    public WorkExperience toModel(WorkExperienceDto workExperienceDto) {
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
