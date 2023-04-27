package com.dynonuggets.refonteimplicaction.job.jobposting.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.job.jobposting.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository.JobPostingRepository;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingAdapter jobPostingAdapter;
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthService authService;

    public JobPostingDto createJob(final JobPostingDto jobPostingDto) {
        final JobPostingModel jobPosting = jobPostingAdapter.toModel(jobPostingDto, authService.getCurrentUser());
        jobPosting.setCreatedAt(Instant.now());
        // si l'utilisateur qui crée une offre est admin, alors elle est validée par défaut
        final UserModel currentUser = authService.getCurrentUser();
        final boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(RoleEnum.ROLE_ADMIN));
        jobPosting.setValid(isAdmin);
        final JobPostingModel jobSaved = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(jobSaved);
    }

    public JobPostingDto getJobById(final Long jobId) {
        final JobPostingModel job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId)));

        final Long currentUserId = authService.getCurrentUser().getId();
        final JobPostingDto jobDto = jobPostingAdapter.toDto(job);

        jobDto.setApply(jobApplicationRepository.findByJob_IdAndUser_id(job.getId(), currentUserId).isPresent());

        return jobDto;
    }

    public Page<JobPostingDto> getAllWithCriteria(final Pageable pageable, final String search, final ContractTypeEnum contractType, final BusinessSectorEnum businessSectorEnum, final Boolean archive, final boolean applyCheck, final Boolean valid) {
        // récupération des jobs
        final Page<JobPostingModel> jobs = jobPostingRepository.findAllWithCriteria(pageable, search, contractType, businessSectorEnum, archive, valid);
        if (applyCheck) {
            final List<Long> jobIds = jobs.stream().map(JobPostingModel::getId).collect(toList());
            final List<Long> jobAppliesIds = getAllAppliesWithJobIdsIn(jobIds, authService.getCurrentUser().getId());
            return jobs.map(job -> {
                final JobPostingDto jobDto = jobPostingAdapter.toDto(job);
                jobDto.setApply(jobAppliesIds.contains(jobDto.getId()));
                return jobDto;
            });
        }
        return jobs.map(jobPostingAdapter::toDto);
    }

    private List<Long> getAllAppliesWithJobIdsIn(final List<Long> jobIds, final Long userId) {
        return jobApplicationRepository.findAllByJob_IdInAndUser_Id(jobIds, userId)
                .stream()
                .map(apply -> apply.getJob().getId())
                .collect(toList());
    }

    @Transactional
    public JobPostingDto saveOrUpdateJobPosting(final JobPostingDto jobPostingDto) {
        final JobPostingModel jobPosting = jobPostingAdapter.toModel(jobPostingDto, authService.getCurrentUser());
        final JobPostingModel save = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(save);
    }

    @Transactional
    public void deleteJobPosting(final Long jobPostingId) {
        final JobPostingModel jobPosting = findById(jobPostingId);
        jobPostingRepository.delete(jobPosting);
    }

    @Transactional
    public JobPostingDto toggleArchiveJobPosting(final Long jobPostingId) {
        final JobPostingModel jobPosting = findById(jobPostingId);
        jobPosting.setArchive(!jobPosting.isArchive());
        final JobPostingModel save = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(save);
    }

    @Transactional
    public List<JobPostingDto> toggleArchiveAll(final List<Long> jobsId) {
        final List<JobPostingModel> jobs = jobPostingRepository.findAllById(jobsId);
        jobs.forEach(job -> job.setArchive(!job.isArchive()));
        return jobPostingRepository.saveAll(jobs)
                .stream()
                .map(jobPostingAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<JobPostingDto> getAllPendingJobs(final Pageable pageable) {
        return jobPostingRepository.findAllByValidIsFalse(pageable)
                .map(jobPostingAdapter::toDto);
    }

    @Transactional
    public JobPostingDto validateJob(final Long jobId) {
        final JobPostingModel job = findById(jobId);
        job.setValid(true);
        final JobPostingModel jobValidate = jobPostingRepository.save(job);

        return jobPostingAdapter.toDto(jobValidate);
    }

    public Page<JobPostingDto> getAllActiveWithCriteria(final Pageable pageable, final String search, final ContractTypeEnum contractType, final BusinessSectorEnum businessSectorEnum, final Boolean isArchive) {
        return getAllWithCriteria(pageable, search, contractType, businessSectorEnum, isArchive, true, true);
    }

    public List<JobPostingDto> getLatestJobs(final int jobsCount) {
        return jobPostingRepository.findAllByArchiveFalseAndValidTrueOrderByCreatedAtDesc(PageRequest.of(0, jobsCount))
                .map(jobPostingAdapter::toDto)
                .getContent();
    }

    private JobPostingModel findById(final Long jobId) {
        return jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId)));
    }
}
