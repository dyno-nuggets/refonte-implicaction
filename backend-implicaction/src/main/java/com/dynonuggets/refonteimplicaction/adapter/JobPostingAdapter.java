package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.core.domain.model.User;
import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;


@Component
@AllArgsConstructor
public class JobPostingAdapter {
    private final CompanyAdapter companyAdapter;

    public JobPostingDto toDto(final JobPosting model) {

        final String username = callIfNotNull(model.getPoster(), User::getUsername);
        final Long userId = callIfNotNull(model.getPoster(), User::getId);
        final CompanyDto companyDto = companyAdapter.toDto(model.getCompany());

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

    public JobPosting toModel(final JobPostingDto dto, final User user) {

        final Company company = companyAdapter.toModel(dto.getCompany());

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
