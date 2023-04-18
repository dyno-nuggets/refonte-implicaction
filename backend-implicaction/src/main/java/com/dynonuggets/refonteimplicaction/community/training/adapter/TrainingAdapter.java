package com.dynonuggets.refonteimplicaction.community.training.adapter;

import com.dynonuggets.refonteimplicaction.community.training.domain.model.TrainingModel;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import org.springframework.stereotype.Component;

@Component
public class TrainingAdapter {
    public TrainingDto toDto(final TrainingModel training) {
        return TrainingDto.builder()
                .id(training.getId())
                .label(training.getLabel())
                .date(training.getDate())
                .school(training.getSchool())
                .build();
    }

    public TrainingModel toModel(final TrainingDto trainingDto) {
        return TrainingModel.builder()
                .id(trainingDto.getId())
                .label(trainingDto.getLabel())
                .date(trainingDto.getDate())
                .school(trainingDto.getSchool())
                .build();
    }
}
