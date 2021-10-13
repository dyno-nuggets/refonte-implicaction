package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobPostingAdapterTest {
    CompanyDto companyDto;
    StatusDto statusDto;
    ContractTypeDto contractTypeDto;
    Company company;
    Status status;
    ContractType contractType;
    JobPosting jobPosting;
    JobPostingAdapter jobPostingAdapter;
    CompanyAdapter companyAdapter;
    StatusAdapter statusAdapter;
    ContractTypeAdapter contractTypeAdapter;

    @BeforeEach
    public void setUp() {
        companyAdapter = new CompanyAdapter();
        statusAdapter = new StatusAdapter();
        contractTypeAdapter = new ContractTypeAdapter();
        jobPostingAdapter = new JobPostingAdapter(companyAdapter, contractTypeAdapter, statusAdapter);
        company = new Company(1L, "urlModel", "logo", "name", "description");
        status = new Status(2L, "label", "type");
        contractType = new ContractType(3L, "label");
        companyDto = new CompanyDto(1L, "urlModel", "logo", "name", "description");
        statusDto = new StatusDto(2L, "label", "type");
        contractTypeDto = new ContractTypeDto(3L, "label");

        jobPosting = JobPosting.builder()
                .id(7L)
                .company(company)
                .title("title")
                .description("description")
                .location("location")
                .salary("salary")
                .keywords("keywords")
                .contractType(contractType)
                .status(status)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void toDtoTest() {

        JobPostingDto jobPostingDto = jobPostingAdapter.toDto(jobPosting);

        assertThat(jobPostingDto.getId()).isEqualTo(jobPosting.getId());
        assertThat(jobPostingDto.getTitle()).isEqualTo(jobPosting.getTitle());
        assertThat(jobPostingDto.getDescription()).isEqualTo(jobPosting.getDescription());
        assertThat(jobPostingDto.getLocation()).isEqualTo(jobPosting.getLocation());
        assertThat(jobPostingDto.getSalary()).isEqualTo(jobPosting.getSalary());
        assertThat(jobPostingDto.getKeywords()).isEqualTo(jobPosting.getKeywords());
        assertThat(jobPostingDto.getStatus()).isEqualTo(statusDto);
        assertThat(jobPostingDto.getContractType()).isEqualTo(contractTypeDto);
        assertThat(jobPostingDto.getCreatedAt()).isEqualTo(jobPosting.getCreatedAt());
    }

    @Test
    void toModelTest() {
        final JobPostingDto dto = jobPostingAdapter.toDto(jobPosting);

        final JobPosting _jobPosting = jobPostingAdapter.toModel(dto);

        assertThat(_jobPosting).usingRecursiveComparison()
                .isEqualTo(jobPosting);
    }
}
