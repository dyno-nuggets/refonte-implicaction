package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class JobPostingAdapter {
    private final CompanyAdapter companyAdapter;

    public JobPostingDto toDto(JobPosting model) {

        CompanyDto companyDto = companyAdapter.toDto(model.getCompany());

        return JobPostingDto.builder()
                .id(model.getId())
                .company(companyDto)
                .title(model.getTitle())
                .shortDescription(model.getShortDescription())
                .description(model.getDescription())
                .location(model.getLocation())
                .salary(model.getSalary())
                .keywords(model.getKeywords())
                .contractType(model.getContractType())
                .createdAt(model.getCreatedAt())
                .build();
    }

    public JobPosting toModel(JobPostingDto dto) {

        Company company = companyAdapter.toModel(dto.getCompany());

        return JobPosting.builder()
                .id(dto.getId())
                .company(company)
                .title(dto.getTitle())
                .shortDescription(dto.getShortDescription())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .salary(dto.getSalary())
                .keywords(dto.getKeywords())
                .contractType(dto.getContractType())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
