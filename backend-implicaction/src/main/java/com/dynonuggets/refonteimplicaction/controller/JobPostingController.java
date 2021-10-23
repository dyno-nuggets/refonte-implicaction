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

    @PostMapping
    public ResponseEntity<JobPostingDto> create(@RequestBody JobPostingDto jobPostingDto) throws ImplicactionException {
        JobPostingDto jobCreated = jobPostingService.createJob(jobPostingDto);
        return ResponseEntity.ok(jobCreated);
    }

    @GetMapping
    public ResponseEntity<Page<JobPostingDto>> getAllByCriteria(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "contractType", defaultValue = "") String contractType
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<JobPostingDto> jobPostingDtos = jobPostingService.findAllWithCriteria(pageable, search, contractType);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(path = "/{jobId}")
    public ResponseEntity<JobPostingDto> getJobById(@PathVariable("jobId") Long jobId) {
        JobPostingDto jobPostingDto = jobPostingService.getJobById(jobId);
        return ResponseEntity.ok(jobPostingDto);
    }

    @PutMapping
    public ResponseEntity<JobPostingDto> update(@RequestBody final JobPostingDto jobPostingDto) {
        JobPostingDto updated = jobPostingService.saveOrUpdateJobPosting(jobPostingDto);
        return ResponseEntity.ok(updated);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{jobId}")
    public ResponseEntity delete(@PathVariable Long jobId) {
        jobPostingService.deleteJobPosting(jobId);
        return ResponseEntity.noContent().build();
    }
}
