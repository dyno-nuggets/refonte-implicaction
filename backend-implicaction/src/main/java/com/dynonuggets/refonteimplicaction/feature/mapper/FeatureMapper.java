package com.dynonuggets.refonteimplicaction.feature.mapper;

import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.model.FeatureModel;
import org.mapstruct.Mapper;

@Mapper
public interface FeatureMapper {
    FeatureDto toDto(FeatureModel model);
}
