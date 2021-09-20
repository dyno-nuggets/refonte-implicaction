package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.model.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingAdapter {
    public TrainingDto toDtoWithoutUser(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .label(training.getLabel())
                .date(training.getDate())
                .school(training.getSchool())
                .build();
    }

    public Training toModel(TrainingDto trainingDto) {
        return Training.builder()
                .id(trainingDto.getId())
                .label(trainingDto.getLabel())
                .date(trainingDto.getDate())
                .school(trainingDto.getSchool())
                .build();
    }
}
