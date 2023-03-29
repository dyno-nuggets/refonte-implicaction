package com.dynonuggets.refonteimplicaction.job.jobapplication.controller;

import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationRequest;
import com.dynonuggets.refonteimplicaction.job.jobapplication.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.job.jobapplication.utils.JobApplicationUris.APPLY_BASE_URI;
import static com.dynonuggets.refonteimplicaction.job.jobapplication.utils.JobApplicationUris.GET_APPLY_URI;

@RestController
@AllArgsConstructor
@RequestMapping(APPLY_BASE_URI)
@PreAuthorize("hasRole('ROLE_PREMIUM')")
public class JobApplicationController {

    private final JobApplicationService applyService;

    @PostMapping
    public ResponseEntity<JobApplicationDto> createApply(@RequestBody final JobApplicationRequest requestDto) {
        final JobApplicationDto saveDto = applyService.createApplyIfNotExists(requestDto);
        final URI location = UriComponentsBuilder.fromPath(APPLY_BASE_URI + GET_APPLY_URI)
                .buildAndExpand(saveDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(saveDto);
    }

    @GetMapping()
    public ResponseEntity<List<JobApplicationDto>> getAllAppliesByUserId() {
        final List<JobApplicationDto> applies = applyService.getAllAppliesForCurrentUser();
        return ResponseEntity.ok(applies);
    }

    @PatchMapping
    public ResponseEntity<JobApplicationDto> updateApply(@RequestBody final JobApplicationRequest requestDto) {
        final JobApplicationDto updateDto = applyService.updateApplyForCurrentUser(requestDto);
        return ResponseEntity.ok(updateDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteApplyByJobId(@RequestParam("jobId") final long jobId) {
        applyService.deleteApplyByJobId(jobId);
        return ResponseEntity.noContent().build();
    }
}
