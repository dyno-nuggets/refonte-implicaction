package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobPostingAdapterTest {
    CompanyDto companyDto;
    ContractTypeDto contractTypeDto;
    Company company;
    ContractType contractType;
    JobPosting jobPosting;
    JobPostingAdapter jobPostingAdapter;
    CompanyAdapter companyAdapter;
    ContractTypeAdapter contractTypeAdapter;

    @BeforeEach
    public void setUp() {
        companyAdapter = new CompanyAdapter();
        contractTypeAdapter = new ContractTypeAdapter();
        jobPostingAdapter = new JobPostingAdapter(companyAdapter, contractTypeAdapter);
        company = new Company(1L, "urlModel", "logo", "name", "description");
        contractType = new ContractType(3L, "label", "code");
        companyDto = new CompanyDto(1L, "urlModel", "logo", "name", "description");
        contractTypeDto = new ContractTypeDto(3L, "label", "code");

        jobPosting = JobPosting.builder()
                .id(7L)
                .company(company)
                .title("title")
                .shortDescription("short_description")
                .description("description")
                .location("location")
                .salary("salary")
                .keywords("keywords")
                .contractType(contractType)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void toDtoTest() {

        JobPostingDto jobPostingDto = jobPostingAdapter.toDto(jobPosting);

        assertThat(jobPostingDto.getId()).isEqualTo(jobPosting.getId());
        assertThat(jobPostingDto.getTitle()).isEqualTo(jobPosting.getTitle());
        assertThat(jobPostingDto.getShortDescription()).isEqualTo(jobPosting.getShortDescription());
        assertThat(jobPostingDto.getDescription()).isEqualTo(jobPosting.getDescription());
        assertThat(jobPostingDto.getLocation()).isEqualTo(jobPosting.getLocation());
        assertThat(jobPostingDto.getSalary()).isEqualTo(jobPosting.getSalary());
        assertThat(jobPostingDto.getKeywords()).isEqualTo(jobPosting.getKeywords());
        assertThat(jobPostingDto.getContractType()).isEqualTo(contractTypeDto);
        assertThat(jobPostingDto.getCreatedAt()).isEqualTo(jobPosting.getCreatedAt());
    }

    @Test
    void toModelTest() {
        final JobPostingDto dto = jobPostingAdapter.toDto(jobPosting);

        final JobPosting expectedJobPosting = jobPostingAdapter.toModel(dto);

        assertThat(jobPosting).usingRecursiveComparison()
                .isEqualTo(expectedJobPosting);
    }
}
