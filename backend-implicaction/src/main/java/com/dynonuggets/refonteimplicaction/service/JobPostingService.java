package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingAdapter jobPostingAdapter;
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthService authService;
    private final NotificationService notificationService;

    public JobPostingDto createJob(JobPostingDto jobPostingDto) {
        JobPosting jobPosting = jobPostingAdapter.toModel(jobPostingDto, authService.getCurrentUser());
        jobPosting.setCreatedAt(Instant.now());
        // si l'utilisateur qui crée une offre est admin, alors elle est validée par défaut
        final User currentUser = authService.getCurrentUser();
        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(RoleEnum.ADMIN.getLongName()));
        jobPosting.setValid(isAdmin);
        JobPosting jobSaved = jobPostingRepository.save(jobPosting);
        if (jobSaved.isValid()) {
            notificationService.createJobNotification(jobSaved);
        }
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

    public Page<JobPostingDto> getAllWithCriteria(Pageable pageable, String search, ContractTypeEnum contractType, BusinessSectorEnum businessSectorEnum, Boolean archive, boolean applyCheck, Boolean valid) {
        // récupération des jobs
        final Page<JobPosting> jobs = jobPostingRepository.findAllWithCriteria(pageable, search, contractType, businessSectorEnum, archive, valid);
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
        JobPosting jobPosting = jobPostingAdapter.toModel(jobPostingDto, authService.getCurrentUser());
        final JobPosting save = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(save);
    }

    @Transactional
    public void deleteJobPosting(Long jobPostingId) {
        JobPosting jobPosting = findById(jobPostingId);
        jobPostingRepository.delete(jobPosting);
    }

    @Transactional
    public JobPostingDto toggleArchiveJobPosting(Long jobPostingId) {
        JobPosting jobPosting = findById(jobPostingId);
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

    @Transactional
    public Page<JobPostingDto> getAllPendingJobs(Pageable pageable) {
        return jobPostingRepository.findAllByValidIsFalse(pageable)
                .map(jobPostingAdapter::toDto);
    }

    @Transactional
    public JobPostingDto validateJob(Long jobId) {
        final JobPosting job = findById(jobId);
        job.setValid(true);
        final JobPosting jobValidate = jobPostingRepository.save(job);

        // creation de la notification
        notificationService.createJobNotification(jobValidate);

        return jobPostingAdapter.toDto(jobValidate);
    }

    public Page<JobPostingDto> getAllActiveWithCriteria(Pageable pageable, String search, ContractTypeEnum contractType, BusinessSectorEnum businessSectorEnum, Boolean isArchive) {
        return getAllWithCriteria(pageable, search, contractType, businessSectorEnum, isArchive, true, true);
    }

    public List<JobPostingDto> getLatestJobs(int jobsCount) {
        return jobPostingRepository.findAllByArchiveFalseAndValidTrueOrderByCreatedAtDesc(PageRequest.of(0, jobsCount))
                .map(jobPostingAdapter::toDto)
                .getContent();
    }

    private JobPosting findById(Long jobId) {
        return jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId)));
    }
}
