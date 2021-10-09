package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
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
                .description(model.getDescription())
                .location(model.getLocation())
                .salary(model.getSalary())
                .keywords(model.getKeywords())
                .contractType(contractTypeDto)
                .status(statusDto)
                .createdAt(model.getCreatedAt())
                .build();
    }
}
