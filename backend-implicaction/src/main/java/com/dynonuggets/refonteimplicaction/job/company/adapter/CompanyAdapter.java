package com.dynonuggets.refonteimplicaction.job.company.adapter;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.Company;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CompanyAdapter {

    public Company toModel(final CompanyDto dto) {
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .logo(dto.getLogo())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .build();
    }

    public CompanyDto toDto(final Company model) {
        return CompanyDto.builder()
                .id(model.getId())
                .name(model.getName())
                .logo(model.getLogo())
                .description(model.getDescription())
                .url(model.getUrl())
                .build();
    }
}
