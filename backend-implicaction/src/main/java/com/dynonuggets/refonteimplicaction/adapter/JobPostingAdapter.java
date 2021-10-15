package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class JobPostingAdapter {
    private final CompanyAdapter companyAdapter;
    private final ContractTypeAdapter contractAdapter;
    private final StatusAdapter statusAdapter;

    public JobPostingDto toDto(JobPosting model) {

        CompanyDto companyDto = companyAdapter.toDto(model.getCompany());
        ContractTypeDto contractTypeDto = contractAdapter.toDto(model.getContractType());
        StatusDto statusDto = statusAdapter.toDto(model.getStatus());

        return JobPostingDto.builder()
                .id(model.getId())
                .company(companyDto)
                .title(model.getTitle())
                .shortDescription(model.getShortDescription())
                .description(model.getDescription())
                .location(model.getLocation())
                .salary(model.getSalary())
                .keywords(model.getKeywords())
                .contractType(contractTypeDto)
                .status(statusDto)
                .createdAt(model.getCreatedAt())
                .build();
    }

    public JobPosting toModel(JobPostingDto dto) {

        Company company = companyAdapter.toModel(dto.getCompany());
        ContractType contractType = contractAdapter.toModel(dto.getContractType());
        Status status = statusAdapter.toModel(dto.getStatus());

        return JobPosting.builder()
                .id(dto.getId())
                .company(company)
                .title(dto.getTitle())
                .shortDescription(dto.getShortDescription())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .salary(dto.getSalary())
                .keywords(dto.getKeywords())
                .contractType(contractType)
                .status(status)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
