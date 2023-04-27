package com.dynonuggets.refonteimplicaction.app.dto;

import com.dynonuggets.refonteimplicaction.app.dto.enums.AppStatus;
import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class App {
    private AppStatus status;
    private List<FeatureDto> features;
}
