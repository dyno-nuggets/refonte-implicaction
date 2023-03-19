package com.dynonuggets.refonteimplicaction.community.controller;

import com.dynonuggets.refonteimplicaction.community.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.community.service.WorkExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.DELETE_EXPERIENCES_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.EXPERIENCES_BASE_URI;

@RestController
@AllArgsConstructor
@RequestMapping(EXPERIENCES_BASE_URI)
public class ExperienceController {

    private final WorkExperienceService experienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceDto> createExperience(@RequestBody final WorkExperienceDto experienceDto) {
        final WorkExperienceDto created = experienceService.saveOrUpdateExperience(experienceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<WorkExperienceDto> updateExperience(@RequestBody final WorkExperienceDto trainingDto) {
        final WorkExperienceDto updated = experienceService.saveOrUpdateExperience(trainingDto);
        return ResponseEntity.ok(updated);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = DELETE_EXPERIENCES_URI)
    public ResponseEntity deleteExperienceById(@PathVariable("experienceId") final Long experienceId) {
        experienceService.deleteExperience(experienceId);
        return ResponseEntity.noContent().build();
    }
}
