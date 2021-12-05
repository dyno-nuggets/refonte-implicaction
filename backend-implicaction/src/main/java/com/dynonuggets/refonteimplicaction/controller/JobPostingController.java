package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.service.JobPostingService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;

@RestController
@RequestMapping(JOBS_BASE_URI)
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
            @RequestParam(value = "contractType", required = false) String contractType,
            @RequestParam(value = "checkApply", required = false) String checkApplyAsString,
            @RequestParam(value = "archive", required = false) String archiveAsString
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final boolean applyCheck = Boolean.parseBoolean(checkApplyAsString);
        final Boolean isArchive = StringUtils.isNotBlank(archiveAsString) ? Boolean.parseBoolean(archiveAsString) : null;
        Page<JobPostingDto> jobPostingDtos = jobPostingService.getAllWithCriteria(pageable, search, contractType, isArchive, applyCheck, null);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(VALIDATED_JOBS)
    public ResponseEntity<Page<JobPostingDto>> getAllActiveByCriteria(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "contractType", required = false) String contractType,
            @RequestParam(value = "archive", required = false) String archiveAsString
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Boolean isArchive = StringUtils.isNotBlank(archiveAsString) ? Boolean.parseBoolean(archiveAsString) : null;
        Page<JobPostingDto> jobPostingDtos = jobPostingService.getAllActiveWithCriteria(pageable, search, contractType, isArchive);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(path = GET_JOB_URI)
    public ResponseEntity<JobPostingDto> getJobById(@PathVariable Long jobId) {
        JobPostingDto jobPostingDto = jobPostingService.getJobById(jobId);
        return ResponseEntity.ok(jobPostingDto);
    }

    @PutMapping
    public ResponseEntity<JobPostingDto> update(@RequestBody final JobPostingDto jobPostingDto) {
        JobPostingDto updated = jobPostingService.saveOrUpdateJobPosting(jobPostingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(DELETE_JOB_URI)
    public ResponseEntity<Void> delete(@PathVariable Long jobId) {
        jobPostingService.deleteJobPosting(jobId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = ARCHIVE_JOB_URI)
    public ResponseEntity<JobPostingDto> toggleArchive(@PathVariable Long jobId) {
        JobPostingDto updated = jobPostingService.toggleArchiveJobPosting(jobId);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(ARCHIVE_JOBS_URI)
    public ResponseEntity<List<JobPostingDto>> toggleArchiveJobs(@RequestBody final List<Long> jobsId) {
        List<JobPostingDto> updated = jobPostingService.toggleArchiveAll(jobsId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(GET_PENDING_JOB_URI)
    public ResponseEntity<Page<JobPostingDto>> getAllPendingJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows
    ) {
        Pageable pageable = PageRequest.of(page, rows);
        Page<JobPostingDto> pendingJobs = jobPostingService.getAllPendingJobs(pageable);
        return ResponseEntity.ok(pendingJobs);
    }

    @PatchMapping(VALIDATE_JOB_URI)
    public ResponseEntity<Void> validateJob(@RequestBody final JobPosting job) {
        jobPostingService.validateJob(job);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_LAST_JOBS_URI)
    public ResponseEntity<List<JobPostingDto>> getLastJobs(@PathVariable int jobsCount) {
        List<JobPostingDto> response = jobPostingService.getLastJobs(jobsCount);
        return ResponseEntity.ok(response);
    }
}
