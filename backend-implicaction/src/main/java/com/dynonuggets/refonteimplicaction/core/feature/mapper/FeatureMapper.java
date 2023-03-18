package com.dynonuggets.refonteimplicaction.core.feature.mapper;

import com.dynonuggets.refonteimplicaction.core.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.core.feature.model.FeatureModel;
import org.mapstruct.Mapper;

@Mapper
public interface FeatureMapper {
    FeatureDto toDto(FeatureModel model);
}
