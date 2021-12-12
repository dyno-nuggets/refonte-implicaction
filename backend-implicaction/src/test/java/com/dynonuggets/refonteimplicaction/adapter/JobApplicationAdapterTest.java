package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.model.*;
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
        User user = User.builder().id(87L).build();
        Company company = new Company(1234L, "World Company", "http://logo.com", "La World Company est une multinationale imaginaire basée aux États-Unis", "http://word-company.com");
        JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        JobApplication model = new JobApplication(123L, job, user, PENDING, Instant.now(), false);

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
        Company company = null;
        User user = User.builder().id(87L).build();
        JobPosting job = new JobPosting(34L, company, "Job de folie", "blablabla", "blablabla", "Paris", "240k", null, CDD, BusinessSectorEnum.ASSURANCE, Instant.now(), false, true, user);
        JobApplication model = new JobApplication(123L, job, user, PENDING, Instant.now(), false);

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
