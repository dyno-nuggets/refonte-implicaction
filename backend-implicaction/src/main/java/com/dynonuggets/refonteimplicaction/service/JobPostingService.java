package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.adapter.ContractTypeAdapter;
import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.adapter.StatusAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.Status;
import com.dynonuggets.refonteimplicaction.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyAdapter companyAdapter;
    private final JobPostingAdapter jobPostingAdapter;
    private final StatusAdapter statusAdapter;
    private final ContractTypeAdapter contractTypeAdapter;

    public JobPostingDto createJob(JobPostingDto jobPostingDto) {

        Company company = companyAdapter.toModel(jobPostingDto.getCompany());
        Status status = statusAdapter.toModel(jobPostingDto.getStatus());
        ContractType contractType = contractTypeAdapter.toModel(jobPostingDto.getContractType());

        JobPosting jobPosting = JobPosting.builder()
                .company(company)
                .title(jobPostingDto.getTitle())
                .description(jobPostingDto.getDescription())
                .location(jobPostingDto.getLocation())
                .salary(jobPostingDto.getSalary())
                .keywords(jobPostingDto.getKeywords())
                .contractType(contractType)
                .status(status)
                .build();

        JobPosting jobSaved = jobPostingRepository.save(jobPosting);
        return jobPostingAdapter.toDto(jobSaved);
    }

    public Page<JobPostingDto> getAll(Pageable pageable, String searchKey) {
        return jobPostingRepository.findAllBySearchKey(pageable, searchKey)
                .map(jobPostingAdapter::toDto);
    }
}
