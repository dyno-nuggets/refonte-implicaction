package com.dynonuggets.refonteimplicaction.feature.dto;

import com.dynonuggets.refonteimplicaction.feature.dto.enums.FeatureKey;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureDto {
    private Long id;
    private FeatureKey featureKey;
    private boolean active;
}
