package com.dynonuggets.refonteimplicaction.community.training.adapter;

import com.dynonuggets.refonteimplicaction.community.training.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import org.springframework.stereotype.Component;

@Component
public class TrainingAdapter {
    public TrainingDto toDto(final Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .label(training.getLabel())
                .date(training.getDate())
                .school(training.getSchool())
                .build();
    }

    public Training toModel(final TrainingDto trainingDto) {
        return Training.builder()
                .id(trainingDto.getId())
                .label(trainingDto.getLabel())
                .date(trainingDto.getDate())
                .school(trainingDto.getSchool())
                .build();
    }
}
