package com.dynonuggets.refonteimplicaction.community.controller;

import com.dynonuggets.refonteimplicaction.community.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.DELETE_TRAINING_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.TRAINING_BASE_URI;
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
