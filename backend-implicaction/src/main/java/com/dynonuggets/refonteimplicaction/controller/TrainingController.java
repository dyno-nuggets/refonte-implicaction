package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody final TrainingDto trainingDto) {
        TrainingDto created = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestBody final TrainingDto trainingDto) {
        TrainingDto updated = trainingService.saveOrUpdateTraining(trainingDto);
        return ResponseEntity.ok(updated);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{trainingId}")
    public ResponseEntity deleteTraining(@PathVariable Long trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }
}
