package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingAdapter jobPostingAdapter;

    public JobPostingDto createJob(JobPostingDto jobPostingDto) {

        JobPosting jobPosting = jobPostingAdapter.toModel(jobPostingDto);
        jobPosting.setCreatedAt(Instant.now());
        JobPosting jobSaved = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(jobSaved);
    }

    public JobPostingDto getJobById(Long jobId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("No job found with id " + jobId));
        return jobPostingAdapter.toDto(jobPosting);
    }

    public Page<JobPostingDto> findAllWithCriteria(Pageable pageable, String search, String contractType) {
        return jobPostingRepository.findAllWithCriteria(pageable, search, contractType)
                .map(jobPostingAdapter::toDto);
    }

    @Transactional
    public JobPostingDto saveOrUpdateJobPosting(final JobPostingDto jobPostingDto) {
        JobPosting jobPosting = jobPostingAdapter.toModel(jobPostingDto);
        final JobPosting save = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(save);
    }

    @Transactional
    public void deleteJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new NotFoundException("Impossible de supprimer l'offre, " + jobPostingId + " n'existe pas."));
        jobPostingRepository.delete(jobPosting);
    }
}
