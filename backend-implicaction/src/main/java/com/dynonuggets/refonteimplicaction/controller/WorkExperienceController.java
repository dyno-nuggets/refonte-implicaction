package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.service.WorkExperienceService;
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
@RequestMapping("/api/experiences")
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;

    @PutMapping(path = "/{userId}")
    public ResponseEntity<List<WorkExperienceDto>> saveAll(@RequestBody List<WorkExperienceDto> workExperienceDtos, @PathVariable Long userId) {
        List<WorkExperienceDto> workExperienceDtoList = workExperienceService.updateByUserId(workExperienceDtos, userId);
        return ResponseEntity.ok(workExperienceDtoList);
    }
}
