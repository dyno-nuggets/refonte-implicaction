package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @PutMapping(path = "/{userId}")
    public ResponseEntity<List<TrainingDto>> saveAll(@RequestBody List<TrainingDto> trainingDtos, @PathVariable Long userId) {
        List<TrainingDto> trainingDtoList = trainingService.updateByUserId(trainingDtos, userId);
        return ResponseEntity.ok(trainingDtoList);
    }
}
