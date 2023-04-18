package com.dynonuggets.refonteimplicaction.job.jobapplication.adapter;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.model.JobApplicationModel;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.job.jobapplication.dto.ApplyStatusEnum.PENDING;
import static com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum.CDD;
import static org.assertj.core.api.Assertions.assertThat;

class JobApplicationAdapterTest {
    JobApplicationAdapter adapter = new JobApplicationAdapter();

    @Test
    void given_company_should_return_dto() {
        // given
        final UserModel user = UserModel.builder().id(87L).build();
        final CompanyModel company = new CompanyModel(1234L, "World Company", "http://logo.com", "La World Company est une multinationale imaginaire basée aux États-Unis", "http://word-company.com");
        final JobPostingModel job = new JobPostingModel(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        final JobApplicationModel model = new JobApplicationModel(123L, job, user, PENDING, Instant.now(), false);

        // when
        final JobApplicationDto actual = adapter.toDto(model);

        // then
        assertThat(actual.getId()).isEqualTo(model.getId());
        assertThat(actual.getCompanyImageUri()).isEqualTo(model.getJob().getCompany().getLogo());
        assertThat(actual.getCompanyName()).isEqualTo(model.getJob().getCompany().getName());
        assertThat(actual.getJobId()).isEqualTo(model.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(model.getJob().getTitle());
        assertThat(actual.getStatusCode()).isEqualTo(model.getStatus().name());
        assertThat(actual.getContractType()).isEqualTo(model.getJob().getContractType());
    }

    @Test
    void given_no_company_should_return_dto() {
        // given
        final CompanyModel company = null;
        final UserModel user = UserModel.builder().id(87L).build();
        final JobPostingModel job = new JobPostingModel(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        final JobApplicationModel model = new JobApplicationModel(123L, job, user, PENDING, Instant.now(), false);

        // when
        final JobApplicationDto actual = adapter.toDto(model);

        // then
        assertThat(actual.getId()).isEqualTo(model.getId());
        assertThat(actual.getCompanyImageUri()).isNull();
        assertThat(actual.getCompanyName()).isNull();
        assertThat(actual.getJobId()).isEqualTo(model.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(model.getJob().getTitle());
        assertThat(actual.getStatusCode()).isEqualTo(model.getStatus().name());
        assertThat(actual.getContractType()).isEqualTo(model.getJob().getContractType());
    }
}
