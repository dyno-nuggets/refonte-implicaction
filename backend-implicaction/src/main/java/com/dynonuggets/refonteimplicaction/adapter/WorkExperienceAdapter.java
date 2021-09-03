package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceAdapter {

    public WorkExperienceDto toDto(WorkExperience workExperience){

        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .startedAt(workExperience.getStartedAt())
                .finishedAt(workExperience.getFinishedAt())
                .label(workExperience.getLabel())
                .description(workExperience.getDescription())
                .build();
    }
}
