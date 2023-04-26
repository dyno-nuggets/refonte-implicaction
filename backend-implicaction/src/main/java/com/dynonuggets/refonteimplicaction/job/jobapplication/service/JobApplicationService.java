package com.dynonuggets.refonteimplicaction.job.jobapplication.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.job.jobapplication.adapter.JobApplicationAdapter;
import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.model.JobApplicationModel;
import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationRequest;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.*;
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
    public JobApplicationDto createApplyIfNotExists(final JobApplicationRequest applyRequest) {
        final JobPostingModel job = jobRepository.findById(applyRequest.getJobId())
                .orElseThrow(() -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, applyRequest.getJobId())));

        final UserModel currentUser = authService.getCurrentUser();

        if (applyRepository.findByJob_IdAndUser_id(job.getId(), currentUser.getId()).isPresent()) {
            throw new IllegalArgumentException(String.format(APPLY_ALREADY_EXISTS_FOR_JOB, job.getId()));
        }

        final JobApplicationModel apply = JobApplicationModel.builder()
                .job(job)
                .archive(false)
                .user(currentUser)
                .status(applyRequest.getStatus())
                .lastUpdate(Instant.now())
                .build();

        final JobApplicationModel applySave = applyRepository.save(apply);

        return applyAdapter.toDto(applySave);
    }

    /**
     * @return les candidatures non archivées de l'utilisateur courant
     */
    @Transactional(readOnly = true)
    public List<JobApplicationDto> getAllAppliesForCurrentUser() {
        final UserModel currentUser = authService.getCurrentUser();

        return applyRepository.findAllByUserAndArchiveIsFalse(currentUser)
                .stream()
                .map(applyAdapter::toDto)
                .collect(toList());
    }

    @Transactional
    public JobApplicationDto updateApplyForCurrentUser(final JobApplicationRequest requestDto) {
        final UserModel currentUser = authService.getCurrentUser();
        final Long jobId = requestDto.getJobId();
        final Long currentUserId = currentUser.getId();

        final JobApplicationModel jobApplication = getJobApplication(jobId, currentUserId);

        jobApplication.setStatus(requestDto.getStatus());

        if (requestDto.getArchive() != null) {
            jobApplication.setArchive(requestDto.getArchive());
        }

        jobApplication.setLastUpdate(Instant.now());
        final JobApplicationModel applySave = applyRepository.save(jobApplication);

        return applyAdapter.toDto(applySave);
    }

    public void deleteApplyByJobId(final long jobId) {
        final long currentUserId = authService.getCurrentUser().getId();
        final JobApplicationModel jobApplication = getJobApplication(jobId, currentUserId);
        applyRepository.delete(jobApplication);
    }

    private JobApplicationModel getJobApplication(final long jobId, final long currentUserId) {
        return applyRepository.findByJob_IdAndUser_id(jobId, currentUserId)
                .orElseThrow(() -> new NotFoundException(String.format(APPLY_NOT_FOUND_WITH_JOB_AND_USER, jobId, currentUserId)));
    }
}
