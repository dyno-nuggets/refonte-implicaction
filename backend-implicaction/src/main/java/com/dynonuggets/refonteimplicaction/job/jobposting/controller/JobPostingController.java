package com.dynonuggets.refonteimplicaction.job.jobposting.controller;

import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.service.JobPostingService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.*;

@RestController
@AllArgsConstructor
@RequestMapping(JOBS_BASE_URI)
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingDto> create(@RequestBody final JobPostingDto jobPostingDto) {
        final JobPostingDto jobCreated = jobPostingService.createJob(jobPostingDto);
        return ResponseEntity.ok(jobCreated);
    }

    @GetMapping
    public ResponseEntity<Page<JobPostingDto>> getAllByCriteria(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder,
            @RequestParam(value = "search", defaultValue = "") final String search,
            @RequestParam(value = "contractType", required = false) final ContractTypeEnum contractType,
            @RequestParam(value = "businessSector", required = false) final BusinessSectorEnum businessSector,
            @RequestParam(value = "checkApply", required = false) final String checkApplyAsString,
            @RequestParam(value = "archive", required = false) final String archiveAsString

    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final boolean applyCheck = Boolean.parseBoolean(checkApplyAsString);
        final Boolean isArchive = StringUtils.isNotBlank(archiveAsString) ? Boolean.parseBoolean(archiveAsString) : null;
        final Page<JobPostingDto> jobPostingDtos = jobPostingService.getAllWithCriteria(pageable, search, contractType, businessSector, isArchive, applyCheck, null);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(VALIDATED_JOBS)
    public ResponseEntity<Page<JobPostingDto>> getAllActiveByCriteria(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder,
            @RequestParam(value = "search", defaultValue = "") final String search,
            @RequestParam(value = "contractType", required = false) final ContractTypeEnum contractType,
            @RequestParam(value = "businessSector", required = false) final BusinessSectorEnum businessSector,
            @RequestParam(value = "archive", required = false) final String archiveAsString
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Boolean isArchive = StringUtils.isNotBlank(archiveAsString) ? Boolean.parseBoolean(archiveAsString) : null;
        final Page<JobPostingDto> jobPostingDtos = jobPostingService.getAllActiveWithCriteria(pageable, search, contractType, businessSector, isArchive);
        return ResponseEntity.ok(jobPostingDtos);
    }

    @GetMapping(path = GET_JOB_URI)
    public ResponseEntity<JobPostingDto> getJobById(@PathVariable final Long jobId) {
        final JobPostingDto jobPostingDto = jobPostingService.getJobById(jobId);
        return ResponseEntity.ok(jobPostingDto);
    }

    @PutMapping
    public ResponseEntity<JobPostingDto> update(@RequestBody final JobPostingDto jobPostingDto) {
        final JobPostingDto updated = jobPostingService.saveOrUpdateJobPosting(jobPostingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(DELETE_JOB_URI)
    public ResponseEntity<Void> delete(@PathVariable final Long jobId) {
        jobPostingService.deleteJobPosting(jobId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = ARCHIVE_JOB_URI)
    public ResponseEntity<JobPostingDto> toggleArchive(@PathVariable final Long jobId) {
        final JobPostingDto updated = jobPostingService.toggleArchiveJobPosting(jobId);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(ARCHIVE_JOBS_URI)
    public ResponseEntity<List<JobPostingDto>> toggleArchiveJobs(@RequestBody final List<Long> jobsId) {
        final List<JobPostingDto> updated = jobPostingService.toggleArchiveAll(jobsId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(GET_PENDING_JOB_URI)
    public ResponseEntity<Page<JobPostingDto>> getAllPendingJobs(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<JobPostingDto> pendingJobs = jobPostingService.getAllPendingJobs(pageable);
        return ResponseEntity.ok(pendingJobs);
    }

    @PatchMapping(VALIDATE_JOB_URI)
    public ResponseEntity<JobPostingDto> validateJob(@PathVariable final Long jobId) {
        final JobPostingDto jobUpdate = jobPostingService.validateJob(jobId);
        return ResponseEntity.ok(jobUpdate);
    }

    @GetMapping(GET_ENABLED_COUNT)
    public ResponseEntity<Long> getEnabledJobsCount() {
        return ResponseEntity.ok(jobPostingService.getEnabledJobsCount());
    }
}
