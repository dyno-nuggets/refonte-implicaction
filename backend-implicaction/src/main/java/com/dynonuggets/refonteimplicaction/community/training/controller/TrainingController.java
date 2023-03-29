package com.dynonuggets.refonteimplicaction.community.training.controller;

import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.training.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.community.training.utils.TrainingUris.DELETE_TRAINING_URI;
import static com.dynonuggets.refonteimplicaction.community.training.utils.TrainingUris.TRAINING_BASE_URI;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(TRAINING_BASE_URI)
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody final TrainingDto trainingDto) {
        final TrainingDto created = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.status(CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestBody final TrainingDto trainingDto) {
        final TrainingDto updated = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(DELETE_TRAINING_URI)
    public ResponseEntity<Void> deleteTraining(@PathVariable final Long trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }
}
