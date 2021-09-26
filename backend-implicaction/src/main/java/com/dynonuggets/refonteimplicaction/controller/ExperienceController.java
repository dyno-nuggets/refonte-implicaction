package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.WorkExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final AuthService authService;
    private final WorkExperienceService workExperienceService;

    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = "/{experienceId}")
    public ResponseEntity deleteExperienceById(@PathVariable("experienceId") Long experienceId) {
        final Long currentUserId = authService.getCurrentUser().getId();
        workExperienceService.deleteExperience(experienceId, currentUserId);
        return ResponseEntity.noContent().build();
    }
}
