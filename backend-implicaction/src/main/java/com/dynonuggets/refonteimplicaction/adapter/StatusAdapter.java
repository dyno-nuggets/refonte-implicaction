package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class StatusAdapter {

    public StatusDto toDto(Status model) {
        return StatusDto.builder()
                .id(model.getId())
                .label(model.getLabel())
                .type(model.getType())
                .build();
    }

    public Status toModel(StatusDto dto) {
        return Status.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .type(dto.getType())
                .build();
    }
}
