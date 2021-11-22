package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.JobApplicationAdapter;
import com.dynonuggets.refonteimplicaction.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.dto.ApplicationRequest;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.utils.Message.APPLY_ALREADY_EXISTS_FOR_JOB;
import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest extends ControllerIntegrationTestBase {

    @Mock
    JobApplicationRepository applyRepostitory;

    @Mock
    JobPostingRepository jobRepository;

    @Mock
    JobApplicationAdapter applyAdapter;

    @Mock
    AuthService authService;

    @InjectMocks
    JobApplicationService jobApplicationService;

    @Test
    void should_create_apply() {
        // given
        ApplicationRequest request = new ApplicationRequest(123L, ApplyStatusEnum.PENDING);
        final ContractType contractType = ContractType.builder().code("CDI").build();
        JobPosting job = new JobPosting(123L, Company.builder().id(23L).build(), "Mon super job", "Il est trop cool", "Blablabla", "Paris", "140k", null, contractType, Instant.now(), false);
        final User currentUser = User.builder().id(45L).build();
        JobApplication jobApplication = new JobApplication(67L, job, currentUser, request.getStatus(), Instant.now(), false);
        JobApplicationDto expectedDto = new JobApplicationDto(jobApplication.getId(), jobApplication.getJob().getId(), jobApplication.getJob().getTitle(), jobApplication.getJob().getCompany().getName(), jobApplication.getJob().getCompany().getLogo(), jobApplication.getStatus(), "CDI");
        given(jobRepository.findById(anyLong())).willReturn(java.util.Optional.of(job));
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(applyRepostitory.save(any())).willReturn(jobApplication);
        given(applyAdapter.toDto(any())).willReturn(expectedDto);

        // when
        JobApplicationDto actual = jobApplicationService.createApplyIfNotExists(request);

        //then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isEqualTo(jobApplication.getId());
        assertThat(actual.getStatus()).isEqualTo(jobApplication.getStatus());
        assertThat(actual.getJobId()).isEqualTo(jobApplication.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(jobApplication.getJob().getTitle());
        assertThat(actual.getContractType()).isEqualTo(jobApplication.getJob().getContractType().getCode());
        assertThat(actual.getCompanyName()).isEqualTo(jobApplication.getJob().getCompany().getName());
        assertThat(actual.getCompanyImageUri()).isEqualTo(jobApplication.getJob().getCompany().getLogo());
    }

    @Test
    void should_throw_notfound_when_creating_with_no_found_job() {
        // given
        long jobId = 123L;
        ApplicationRequest request = new ApplicationRequest(jobId, ApplyStatusEnum.PENDING);
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
        ApplicationRequest request = new ApplicationRequest(jobId, ApplyStatusEnum.PENDING);
        IllegalArgumentException expectedException = new IllegalArgumentException(String.format(APPLY_ALREADY_EXISTS_FOR_JOB, jobId));
        given(jobRepository.findById(anyLong())).willThrow(expectedException);

        // when
        final IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> jobApplicationService.createApplyIfNotExists(request));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }
}
