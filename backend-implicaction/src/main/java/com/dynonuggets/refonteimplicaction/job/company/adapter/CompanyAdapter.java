package com.dynonuggets.refonteimplicaction.job.company.adapter;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CompanyAdapter {

    public CompanyModel toModel(final CompanyDto dto) {
        return CompanyModel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .logo(dto.getLogo())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .build();
    }

    public CompanyDto toDto(final CompanyModel model) {
        return CompanyDto.builder()
                .id(model.getId())
                .name(model.getName())
                .logo(model.getLogo())
                .description(model.getDescription())
                .url(model.getUrl())
                .build();
    }
}
