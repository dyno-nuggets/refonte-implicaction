package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.model.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobApplication;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum.PENDING;
import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.CDD;
import static org.assertj.core.api.Assertions.assertThat;

class JobApplicationAdapterTest extends ControllerIntegrationTestBase {
    JobApplicationAdapter adapter = new JobApplicationAdapter();

    @Test
    void given_company_should_return_dto() {
        // given
        final User user = User.builder().id(87L).build();
        final Company company = new Company(1234L, "World Company", "http://logo.com", "La World Company est une multinationale imaginaire basée aux États-Unis", "http://word-company.com");
        final JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        final JobApplication model = new JobApplication(123L, job, user, PENDING, Instant.now(), false);

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
        final Company company = null;
        final User user = User.builder().id(87L).build();
        final JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        final JobApplication model = new JobApplication(123L, job, user, PENDING, Instant.now(), false);

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
