package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CompanyAdapter {

    public Company toModel(CompanyDto dto) {
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .logo(dto.getLogo())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .build();
    }

    public CompanyDto toDto(Company model) {
        return CompanyDto.builder()
                .id(model.getId())
                .name(model.getName())
                .logo(model.getLogo())
                .description(model.getDescription())
                .url(model.getUrl())
                .build();
    }
}
