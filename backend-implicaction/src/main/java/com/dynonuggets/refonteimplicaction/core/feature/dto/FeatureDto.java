package com.dynonuggets.refonteimplicaction.core.feature.dto;

import com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureDto {
    private Long id;
    private FeatureKey featureKey;
    private Boolean active;
}
