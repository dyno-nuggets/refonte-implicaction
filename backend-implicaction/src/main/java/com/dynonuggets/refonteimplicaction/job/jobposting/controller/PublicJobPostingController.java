package com.dynonuggets.refonteimplicaction.job.jobposting.controller;

import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.service.JobPostingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.GET_LATEST_JOBS_URI;
import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.PUBLIC_JOBS_BASE_URI;

@RestController
@AllArgsConstructor
@RequestMapping(PUBLIC_JOBS_BASE_URI)
public class PublicJobPostingController {

    private final JobPostingService jobPostingService;

    @GetMapping(GET_LATEST_JOBS_URI)
    public ResponseEntity<List<JobPostingDto>> getLatestJobs(@RequestParam(value = "rows", defaultValue = "10") final int rows) {
        final List<JobPostingDto> response = jobPostingService.getLatestJobs(rows);
        return ResponseEntity.ok(response);
    }
}
