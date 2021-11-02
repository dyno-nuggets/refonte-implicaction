package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor

public class ContractTypeAdapter {

    public ContractTypeDto toDto(ContractType model) {
        return ContractTypeDto.builder()
                .id(model.getId())
                .label(model.getLabel())
                .code(model.getCode())
                .build();
    }

    public ContractType toModel(ContractTypeDto dto) {
        return ContractType.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .code(dto.getCode())
                .build();
    }
}
