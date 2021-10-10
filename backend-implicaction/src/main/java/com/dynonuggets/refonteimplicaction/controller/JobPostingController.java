package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.service.JobPostingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-postings")
@AllArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping("/")
    public ResponseEntity<JobPostingDto> create(@RequestBody JobPostingDto jobPostingDto) throws ImplicactionException {
        JobPostingDto jobCreated = jobPostingService.createJob(jobPostingDto);
        return ResponseEntity.ok(jobCreated);
    }

    @GetMapping
    public ResponseEntity<Page<JobPostingDto>> getAllBySearchKey(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "searchKey", defaultValue = "") String searchKey
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<JobPostingDto> jobPostingDtos = jobPostingService.getAllBySearchKey(pageable, searchKey);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(path = "/{jobId}")
    public ResponseEntity<JobPostingDto> getJobById(@PathVariable("jobId") Long jobId) {
        JobPostingDto jobPostingDto = jobPostingService.getJobById(jobId);
        return ResponseEntity.ok(jobPostingDto);
    }
}
