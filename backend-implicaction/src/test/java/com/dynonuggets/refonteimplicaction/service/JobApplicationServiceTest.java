package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobApplicationAdapter;
import com.dynonuggets.refonteimplicaction.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationRequest;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum.*;
import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.CDD;
import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.CDI;
import static com.dynonuggets.refonteimplicaction.utils.Message.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest extends ControllerIntegrationTestBase {

    @Mock
    JobApplicationRepository applyRepository;

    @Mock
    JobPostingRepository jobRepository;

    @Mock
    JobApplicationAdapter applyAdapter;

    @Mock
    AuthService authService;

    @InjectMocks
    JobApplicationService jobApplicationService;

    @Captor
    ArgumentCaptor<JobApplication> argumentCaptor;

    @Test
    void should_create_apply() {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, PENDING, null);
        final User currentUser = User.builder().id(45L).build();
        JobPosting job = new JobPosting(123L, Company.builder().id(23L).build(), "Mon super job", "Il est trop cool", "Blablabla", "Paris", "140k", null, CDI, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, currentUser);
        JobApplication jobApplication = new JobApplication(67L, job, currentUser, request.getStatus(), Instant.now(), false);
        JobApplicationDto expectedDto = new JobApplicationDto(jobApplication.getId(), jobApplication.getJob().getId(), jobApplication.getJob().getTitle(), jobApplication.getJob().getCompany().getName(), jobApplication.getJob().getCompany().getLogo(), jobApplication.getStatus().name(), "Paris (75)", CDI, false);
        given(jobRepository.findById(anyLong())).willReturn(Optional.of(job));
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.save(any())).willReturn(jobApplication);
        given(applyAdapter.toDto(any())).willReturn(expectedDto);

        // when
        JobApplicationDto actual = jobApplicationService.createApplyIfNotExists(request);

        // then
        assertThat(actual.getId()).isEqualTo(jobApplication.getId());
        assertThat(actual.getStatusCode()).isEqualTo(jobApplication.getStatus().name());
        assertThat(actual.getJobId()).isEqualTo(jobApplication.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(jobApplication.getJob().getTitle());
        assertThat(actual.getContractType()).isEqualTo(jobApplication.getJob().getContractType());
        assertThat(actual.getCompanyName()).isEqualTo(jobApplication.getJob().getCompany().getName());
        assertThat(actual.getCompanyImageUri()).isEqualTo(jobApplication.getJob().getCompany().getLogo());

    }

    @Test
    void should_throw_notfound_when_creating_with_no_found_job() {
        // given
        long jobId = 123L;
        JobApplicationRequest request = new JobApplicationRequest(jobId, PENDING, null);
        NotFoundException expectedException = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobRepository.findById(anyLong())).willThrow(expectedException);

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> jobApplicationService.createApplyIfNotExists(request));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_throw_exception_when_creating_with_already_applied_job() {
        // given
        long jobId = 123L;
        JobApplicationRequest request = new JobApplicationRequest(jobId, PENDING, null);
        IllegalArgumentException expectedException = new IllegalArgumentException(String.format(APPLY_ALREADY_EXISTS_FOR_JOB, jobId));
        final JobApplication apply = JobApplication.builder().id(123L).build();
        User currentUser = User.builder().id(123L).build();
        JobPosting job = new JobPosting(123L, Company.builder().id(23L).build(), "Mon super job", "Il est trop cool", "Blablabla", "Paris", "140k", null, CDI, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, currentUser);
        given(jobRepository.findById(anyLong())).willReturn(Optional.of(job));
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findByJob_IdAndUser_id(anyLong(), anyLong())).willReturn(Optional.of(apply));

        // when
        final IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> jobApplicationService.createApplyIfNotExists(request));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_return_all_users_apply() {
        // given
        User currentUser = User.builder().id(123L).build();
        List<JobApplication> expecteds = asList(
                new JobApplication(1L, JobPosting.builder().id(12L).build(), currentUser, PENDING, Instant.now(), false),
                new JobApplication(2L, JobPosting.builder().id(13L).build(), currentUser, CHASED, Instant.now(), false),
                new JobApplication(3L, JobPosting.builder().id(14L).build(), currentUser, INTERVIEW, Instant.now(), false)
        );
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findAllByUserAndArchiveIsFalse(any())).willReturn(expecteds);

        // when
        final List<JobApplicationDto> allAppliesForCurrentUser = jobApplicationService.getAllAppliesForCurrentUser();

        // then
        assertThat(allAppliesForCurrentUser.size()).isEqualTo(expecteds.size());
    }

    @Test
    void should_update_apply_for_current_user_if_apply_exists() {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, INTERVIEW, null);
        User currentUser = User.builder().id(123L).build();
        final JobApplication jobApplication = new JobApplication(1L, JobPosting.builder().id(12L).build(), currentUser, PENDING, Instant.now(), false);
        final JobApplication jobApplicationUpdate = new JobApplication(1L, JobPosting.builder().id(12L).build(), currentUser, PENDING, Instant.now(), false);
        final JobApplicationDto expectedDto = new JobApplicationDto(1L, 12L, "JobTitle", "companyName", "http://image.url", INTERVIEW.name(), "Paris", CDD, false);
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findByJob_IdAndUser_id(anyLong(), anyLong())).willReturn(Optional.of(jobApplication));
        given(applyRepository.save(any())).willReturn(jobApplicationUpdate);
        given(applyAdapter.toDto(any())).willReturn(expectedDto);

        // when
        final JobApplicationDto actualDto = jobApplicationService.updateApplyForCurrentUser(request);

        // then
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void should_update_archive_for_current_user_if_archive_from_request_is_not_null() {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, INTERVIEW, null);
        User currentUser = User.builder().id(123L).build();
        final JobApplication jobApplication = new JobApplication(1L, JobPosting.builder().id(12L).build(), currentUser, PENDING, Instant.now(), false);
        final JobApplication jobApplicationUpdate = new JobApplication(1L, JobPosting.builder().id(12L).build(), currentUser, PENDING, Instant.now(), true);
        final JobApplicationDto expectedDto = new JobApplicationDto(1L, 12L, "JobTitle", "companyName", "http://image.url", INTERVIEW.name(), "Paris", CDD, true);
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findByJob_IdAndUser_id(anyLong(), anyLong())).willReturn(Optional.of(jobApplication));
        given(applyRepository.save(any())).willReturn(jobApplicationUpdate);
        given(applyAdapter.toDto(any())).willReturn(expectedDto);

        // when
        final JobApplicationDto actualDto = jobApplicationService.updateApplyForCurrentUser(request);

        // then
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void should_throw_exception_when_update_and_job_not_exists() {
        JobApplicationRequest request = new JobApplicationRequest(123L, INTERVIEW, null);
        User currentUser = User.builder().id(123L).build();
        final NotFoundException expectedException = new NotFoundException(
                String.format(APPLY_NOT_FOUND_WITH_JOB_AND_USER, request.getJobId(), currentUser.getId())
        );
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findByJob_IdAndUser_id(anyLong(), anyLong())).willThrow(expectedException);

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> jobApplicationService.updateApplyForCurrentUser(request));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_delete_apply_when_exists() {
        // given
        long jobId = 123L;
        final User currentUser = User.builder().id(123L).build();
        final JobApplication jobApplication = JobApplication.builder().build();
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepository.findByJob_IdAndUser_id(anyLong(), anyLong())).willReturn(Optional.ofNullable(jobApplication));

        // when
        jobApplicationService.deleteApplyByJobId(jobId);

        // then
        verify(applyRepository, times(1)).delete(argumentCaptor.capture());
    }

    @Test
    void should_throw_exception_when_deleting_if_apply_not_exists() {
        // given
        long jobId = 123L;
        long currentUserId = 321L;
        final User currentUser = User.builder().id(currentUserId).build();
        final NotFoundException expectedException = new NotFoundException(String.format(APPLY_NOT_FOUND_WITH_JOB_AND_USER, jobId, currentUserId));
        given(authService.getCurrentUser()).willReturn(currentUser);

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> jobApplicationService.deleteApplyByJobId(jobId));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }
}
