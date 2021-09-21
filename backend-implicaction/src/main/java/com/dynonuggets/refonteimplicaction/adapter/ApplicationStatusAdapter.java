package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.ApplicationStatusDto;
import com.dynonuggets.refonteimplicaction.model.Status;

public class ApplicationStatusAdapter {

    public ApplicationStatusDto toDto(Status model) {
        return ApplicationStatusDto.builder()
                .id(model.getId())
                .label(model.getLabel())
                .build();
    }
}
