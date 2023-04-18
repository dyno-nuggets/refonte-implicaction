package com.dynonuggets.refonteimplicaction.job.jobposting.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.repository.JobApplicationRepository;
import com.dynonuggets.refonteimplicaction.job.jobposting.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository.JobPostingRepository;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum.ASSURANCE;
import static com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum.CDD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JobPostingServiceTest {

    @Mock
    JobPostingRepository jobPostingRepository;
    @Mock
    JobPostingAdapter jobPostingAdapter;
    @Mock
    JobApplicationRepository jobApplicationRepository;
    @Mock
    AuthService authService;
    @InjectMocks
    JobPostingService JobPostingService;

    @Test
    void should_get_all_jobs_with_criteria_and_check_apply() {
        // given
        final List<JobPostingModel> jobs = Collections.singletonList(JobPostingModel.builder().id(123L).contractType(CDD).build());
        final Page<JobPostingModel> jobPage = new PageImpl<>(jobs);
        final Pageable pageable = PageRequest.of(0, 10);
        final UserModel currentUser = UserModel.builder().id(123L).build();
        given(jobPostingRepository.findAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean())).willReturn(jobPage);
        given(jobApplicationRepository.findAllByJob_IdInAndUser_Id(anyList(), anyLong())).willReturn(Collections.emptyList());
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(jobPostingAdapter.toDto(any())).willReturn(JobPostingDto.builder().build());

        // when
        final Page<JobPostingDto> actual = JobPostingService.getAllWithCriteria(pageable, "", CDD, ASSURANCE, false, true, true);

        // then
        assertThat(actual.getTotalElements()).isEqualTo(jobPage.getTotalElements());
    }

    @Test
    void should_get_all_jobs_with_criteria_and_check_apply_false() {
        // given
        final List<JobPostingModel> jobs = Collections.singletonList(JobPostingModel.builder().id(123L).contractType(CDD).build());
        final Page<JobPostingModel> jobPage = new PageImpl<>(jobs);
        final Pageable pageable = PageRequest.of(0, 10);
        given(jobPostingRepository.findAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean())).willReturn(jobPage);

        // when
        final Page<JobPostingDto> actual = JobPostingService.getAllWithCriteria(pageable, "", CDD, ASSURANCE, false, false, true);

        // then
        assertThat(actual.getTotalElements()).isEqualTo(jobPage.getTotalElements());
    }
}
