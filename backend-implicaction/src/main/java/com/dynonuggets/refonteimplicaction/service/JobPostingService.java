package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import com.dynonuggets.refonteimplicaction.utils.Message;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingAdapter jobPostingAdapter;
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthService authService;

    public JobPostingDto createJob(JobPostingDto jobPostingDto) {

        JobPosting jobPosting = jobPostingAdapter.toModel(jobPostingDto);
        jobPosting.setCreatedAt(Instant.now());
        JobPosting jobSaved = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(jobSaved);
    }

    public JobPostingDto getJobById(Long jobId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId)));

        final Long currentUserId = authService.getCurrentUser().getId();
        final JobPostingDto jobDto = jobPostingAdapter.toDto(job);

        jobDto.setApply(jobApplicationRepository.findByJob_IdAndUser_id(job.getId(), currentUserId).isPresent());

        return jobDto;
    }

    public Page<JobPostingDto> getAllWithCriteria(Pageable pageable, String search, String contractType, boolean applyCheck) {
        // récupération des jobs
        final Page<JobPosting> jobs = jobPostingRepository.findAllWithCriteria(pageable, search, contractType);
        if (applyCheck) {
            final List<Long> jobIds = jobs.stream().map(JobPosting::getId).collect(toList());
            final List<Long> jobAppliesIds = getAllAppliesWithJobIdsIn(jobIds, authService.getCurrentUser().getId());
            return jobs.map(job -> {
                final JobPostingDto jobDto = jobPostingAdapter.toDto(job);
                jobDto.setApply(jobAppliesIds.contains(jobDto.getId()));
                return jobDto;
            });
        }
        return jobs.map(jobPostingAdapter::toDto);
    }

    private List<Long> getAllAppliesWithJobIdsIn(List<Long> jobIds, Long userId) {
        return jobApplicationRepository.findAllByJob_IdInAndUser_Id(jobIds, userId)
                .stream()
                .map(apply -> apply.getJob().getId())
                .collect(toList());
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
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobPostingId)));
        jobPostingRepository.delete(jobPosting);
    }

    @Transactional
    public JobPostingDto toggleArchiveJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new NotFoundException(String.format(Message.JOB_NOT_FOUND_MESSAGE, jobPostingId)));
        jobPosting.setArchive(!jobPosting.isArchive());
        final JobPosting save = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(save);
    }

    @Transactional
    public List<JobPostingDto> toggleArchiveAll(List<Long> jobsId) {
        List<JobPosting> jobs = jobPostingRepository.findAllById(jobsId);
        jobs.forEach(job -> job.setArchive(!job.isArchive()));
        return jobPostingRepository.saveAll(jobs)
                .stream()
                .map(jobPostingAdapter::toDto)
                .collect(Collectors.toList());
    }
}
