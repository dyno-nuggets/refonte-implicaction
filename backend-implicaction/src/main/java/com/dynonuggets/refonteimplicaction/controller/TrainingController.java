package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.DELETE_TRAINING_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.TRAINING_BASE_URI;

@RestController
@AllArgsConstructor
@RequestMapping(TRAINING_BASE_URI)
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody final TrainingDto trainingDto) {
        TrainingDto created = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestBody final TrainingDto trainingDto) {
        TrainingDto updated = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.ok(updated);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(DELETE_TRAINING_URI)
    public ResponseEntity deleteTraining(@PathVariable Long trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }
}
