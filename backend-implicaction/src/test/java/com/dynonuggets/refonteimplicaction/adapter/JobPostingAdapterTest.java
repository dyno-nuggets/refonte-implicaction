package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.CDD;
import static org.assertj.core.api.Assertions.assertThat;

class JobPostingAdapterTest {
    CompanyDto companyDto;
    Company company;
    JobPosting jobPosting;
    JobPostingAdapter jobPostingAdapter;
    CompanyAdapter companyAdapter;

    @BeforeEach
    public void setUp() {
        companyAdapter = new CompanyAdapter();
        jobPostingAdapter = new JobPostingAdapter(companyAdapter);
        company = new Company(1L, "urlModel", "logo", "name", "description");
        companyDto = new CompanyDto(1L, "urlModel", "logo", "name", "description");

        jobPosting = JobPosting.builder()
                .id(7L)
                .company(company)
                .title("title")
                .shortDescription("short_description")
                .description("description")
                .location("location")
                .salary("salary")
                .keywords("keywords")
                .contractType(CDD)
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
        assertThat(jobPostingDto.getContractType()).isEqualTo(CDD);
        assertThat(jobPostingDto.getCreatedAt()).isEqualTo(jobPosting.getCreatedAt());
    }

    @Test
    void toModelTest() {
        final JobPostingDto dto = jobPostingAdapter.toDto(jobPosting);

        User user = User.builder()
                .id(1L)
                .username("User")
                .build();
        final JobPosting expectedJobPosting = jobPostingAdapter.toModel(dto, user);

        assertThat(jobPosting).usingRecursiveComparison()
                .isEqualTo(expectedJobPosting);
    }
}
