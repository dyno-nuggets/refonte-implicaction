package com.dynonuggets.refonteimplicaction.job.jobposting.adapter;

import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.job.company.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum.CDD;
import static org.assertj.core.api.Assertions.assertThat;

class JobPostingAdapterTest {
    CompanyDto companyDto;
    CompanyModel company;
    JobPostingModel jobPosting;
    JobPostingDto expectedJobPostingDto;
    JobPostingAdapter jobPostingAdapter;
    CompanyAdapter companyAdapter;
    UserModel user;
    Instant createdAt;

    @BeforeEach
    public void setUp() {
        companyAdapter = new CompanyAdapter();
        jobPostingAdapter = new JobPostingAdapter(companyAdapter);
        company = new CompanyModel(1L, "urlModel", "logo", "name", "description");
        companyDto = new CompanyDto(1L, "urlModel", "logo", "name", "description");
        user = UserModel.builder().id(2L).username("user").build();
        createdAt = Instant.now();

        jobPosting = JobPostingModel.builder()
                .id(7L)
                .company(company)
                .title("title")
                .shortDescription("short_description")
                .description("description")
                .location("location")
                .salary("salary")
                .keywords("keywords")
                .contractType(CDD)
                .businessSector(BusinessSectorEnum.ASSURANCE)
                .createdAt(createdAt)
                .archive(false)
                .valid(true)
                .poster(user)
                .build();

        expectedJobPostingDto = JobPostingDto.builder()
                .id(7L)
                .company(companyDto)
                .title("title")
                .shortDescription("short_description")
                .description("description")
                .location("location")
                .salary("salary")
                .keywords("keywords")
                .contractType(CDD)
                .businessSector(BusinessSectorEnum.ASSURANCE)
                .createdAt(createdAt)
                .archive(false)
                .valid(true)
                .posterId(user.getId())
                .posterName(user.getUsername())
                .build();
    }

    @Test
    void toDtoTest() {
        final JobPostingDto jobPostingDto = jobPostingAdapter.toDto(jobPosting);

        assertThat(jobPostingDto)
                .usingRecursiveComparison()
                .ignoringFields("durationAsString")
                .isEqualTo(expectedJobPostingDto);
        assertThat(jobPostingDto.getDurationAsString()).isNotEmpty();
    }

    @Test
    void toModelTest() {
        final JobPostingDto dto = jobPostingAdapter.toDto(jobPosting);

        final JobPostingModel expectedJobPosting = jobPostingAdapter.toModel(dto, user);

        assertThat(jobPosting)
                .usingRecursiveComparison()
                .isEqualTo(expectedJobPosting);
    }
}
