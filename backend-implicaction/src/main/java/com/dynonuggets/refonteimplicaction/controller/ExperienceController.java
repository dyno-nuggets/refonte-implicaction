package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.service.WorkExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/experiences")
public class ExperienceController {


    private final WorkExperienceService workExperienceService;

    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = "/{experienceId}")
    public ResponseEntity deleteExperienceById(@PathVariable("experienceId") Long experienceId) {
        workExperienceService.deleteExperience(experienceId);
        return ResponseEntity.noContent().build();
    }
}
