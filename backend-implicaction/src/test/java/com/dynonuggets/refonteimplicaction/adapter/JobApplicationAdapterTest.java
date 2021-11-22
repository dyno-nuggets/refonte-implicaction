package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.model.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobApplicationAdapterTest extends ControllerIntegrationTestBase {

    JobApplicationAdapter adapter = new JobApplicationAdapter();

    @Test
    void given_company_should_return_dto() {
        // given
        Company company = new Company(1234L, "World Company", "http://logo.com", "La World Company est une multinationale imaginaire basée aux États-Unis", "http://word-company.com");
        ContractType contractType = new ContractType(234L, "CDD", "CDD");
        JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, contractType, Instant.now(), false);
        User user = User.builder().id(87L).build();
        JobApplication model = new JobApplication(123L, job, user, ApplyStatusEnum.PENDING, Instant.now(), false);

        // when
        final JobApplicationDto actual = adapter.toDto(model);

        // then
        assertThat(actual.getId()).isEqualTo(model.getId());
        assertThat(actual.getCompanyImageUri()).isEqualTo(model.getJob().getCompany().getLogo());
        assertThat(actual.getCompanyName()).isEqualTo(model.getJob().getCompany().getName());
        assertThat(actual.getJobId()).isEqualTo(model.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(model.getJob().getTitle());
        assertThat(actual.getStatus()).isEqualTo(model.getStatus());
        assertThat(actual.getContractType()).isEqualTo(model.getJob().getContractType().getCode());
    }

    @Test
    void given_no_company_should_return_dto() {
        // given
        Company company = null;
        ContractType contractType = new ContractType(234L, "CDD", "CDD");
        JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, contractType, Instant.now(), false);
        User user = User.builder().id(87L).build();
        JobApplication model = new JobApplication(123L, job, user, ApplyStatusEnum.PENDING, Instant.now(), false);

        // when
        final JobApplicationDto actual = adapter.toDto(model);

        // then
        assertThat(actual.getId()).isEqualTo(model.getId());
        assertThat(actual.getCompanyImageUri()).isNull();
        assertThat(actual.getCompanyName()).isNull();
        assertThat(actual.getJobId()).isEqualTo(model.getJob().getId());
        assertThat(actual.getJobTitle()).isEqualTo(model.getJob().getTitle());
        assertThat(actual.getStatus()).isEqualTo(model.getStatus());
        assertThat(actual.getContractType()).isEqualTo(model.getJob().getContractType().getCode());
    }
}
