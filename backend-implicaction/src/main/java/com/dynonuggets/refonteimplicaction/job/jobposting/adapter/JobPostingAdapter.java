package com.dynonuggets.refonteimplicaction.job.jobposting.adapter;

import com.dynonuggets.refonteimplicaction.job.company.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.job.company.domain.model.Company;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPosting;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.core.utils.DateUtils.getDurationAsString;
import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;


@Component
@AllArgsConstructor
public class JobPostingAdapter {
    private final CompanyAdapter companyAdapter;

    public JobPostingDto toDto(final JobPosting model) {

        final String username = callIfNotNull(model.getPoster(), UserModel::getUsername);
        final Long userId = callIfNotNull(model.getPoster(), UserModel::getId);
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
                .durationAsString(getDurationAsString(model.getCreatedAt()))
                .archive(model.isArchive())
                .businessSector(model.getBusinessSector())
                .valid(model.isValid())
                .posterName(username)
                .posterId(userId)
                .build();
    }

    public JobPosting toModel(final JobPostingDto dto, final UserModel user) {

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
