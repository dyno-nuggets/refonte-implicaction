package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyAdapter companyAdapter;
    private final JobPostingAdapter jobPostingAdapter;

    public JobPostingDto registerOffer(JobPostingDto jobPostingDto) {

        Company company = companyAdapter.toModel(jobPostingDto.getCompany());

        JobPosting jobPosting = JobPosting.builder()
                .company(company)
                .title(jobPostingDto.getTitle())
                .description(jobPostingDto.getDescription())
                .location(jobPostingDto.getLocation())
                .salary(jobPostingDto.getSalary())
                .keywords(jobPostingDto.getKeywords())
                .contractType(jobPostingDto.getContractType())
                .status(jobPostingDto.getStatus())
                .build();

        JobPosting jobSaved = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(jobSaved);
    }
}
