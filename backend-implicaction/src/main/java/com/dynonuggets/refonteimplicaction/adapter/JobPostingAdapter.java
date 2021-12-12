package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class JobPostingAdapter {
    private final CompanyAdapter companyAdapter;

    public JobPostingDto toDto(JobPosting model) {

        final String username = model.getPoster() != null ? model.getPoster().getUsername() : "";
        final Long userId = model.getPoster() != null ? model.getPoster().getId() : null;
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
                .archive(model.isArchive())
                .businessSector(model.getBusinessSector())
                .valid(model.isValid())
                .posterName(username)
                .posterId(userId)
                .build();
    }

    public JobPosting toModel(JobPostingDto dto, User user) {

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
                .businessSector(dto.getBusinessSector())
                .createdAt(dto.getCreatedAt())
                .archive(dto.isArchive())
                .valid(dto.isValid())
                .poster(user)
                .build();
    }
}
