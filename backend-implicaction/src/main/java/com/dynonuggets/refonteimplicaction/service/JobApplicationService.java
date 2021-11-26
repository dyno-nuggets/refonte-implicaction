package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobApplicationAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationRequest;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.JobApplication;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.Message.*;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository applyRepository;
    private final JobPostingRepository jobRepository;
    private final JobApplicationAdapter applyAdapter;
    private final AuthService authService;

    /**
     * @return crée une candidature s'il l'utilisateur courant n'a pas déjà candidaté pour l'offre
     */
    @Transactional
    public JobApplicationDto createApplyIfNotExists(JobApplicationRequest applyRequest) {
        final JobPosting job = jobRepository.findById(applyRequest.getJobId())
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, applyRequest.getJobId())));

        final User currentUser = authService.getCurrentUser();

        if (applyRepository.findByJob_IdAndUser_id(job.getId(), currentUser.getId()).isPresent()) {
            throw new IllegalArgumentException(String.format(APPLY_ALREADY_EXISTS_FOR_JOB, job.getId()));
        }

        final JobApplication apply = JobApplication.builder()
                .job(job)
                .archive(false)
                .user(currentUser)
                .status(applyRequest.getStatus())
                .lastUpdate(Instant.now())
                .build();

        final JobApplication applySave = applyRepository.save(apply);

        return applyAdapter.toDto(applySave);
    }

    /**
     * @return les candidatures non archivées de l'utilisateur courant
     */
    @Transactional(readOnly = true)
    public List<JobApplicationDto> getAllAppliesForCurrentUser() {
        final User currentUser = authService.getCurrentUser();

        return applyRepository.findAllByUserAndArchiveIsFalse(currentUser)
                .stream()
                .map(applyAdapter::toDto)
                .collect(toList());
    }

    @Transactional
    public JobApplicationDto updateApplyForCurrentUser(JobApplicationRequest requestDto) {
        final User currentUser = authService.getCurrentUser();
        final Long jobId = requestDto.getJobId();
        final Long currentUserId = currentUser.getId();

        final JobApplication jobApplication = getJobApplication(jobId, currentUserId);

        jobApplication.setStatus(requestDto.getStatus());

        if (requestDto.getArchive() != null) {
            jobApplication.setArchive(requestDto.getArchive());
        }

        jobApplication.setLastUpdate(Instant.now());
        final JobApplication applySave = applyRepository.save(jobApplication);

        return applyAdapter.toDto(applySave);
    }

    public void deleteApplyByJobId(long jobId) {
        final long currentUserId = authService.getCurrentUser().getId();
        final JobApplication jobApplication = getJobApplication(jobId, currentUserId);
        applyRepository.delete(jobApplication);
    }

    private JobApplication getJobApplication(long jobId, long currentUserId) {
        return applyRepository.findByJob_IdAndUser_id(jobId, currentUserId)
                .orElseThrow(() -> new NotFoundException(String.format(APPLY_NOT_FOUND_WITH_JOB_AND_USER, jobId, currentUserId)));
    }
}
