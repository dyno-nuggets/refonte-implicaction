package com.dynonuggets.refonteimplicaction.community.training.mapper;

import com.dynonuggets.refonteimplicaction.community.training.domain.model.TrainingModel;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import org.mapstruct.Mapper;

@Mapper
public interface TrainingMapper {

    TrainingDto toDto(final TrainingModel model);

    TrainingModel toModel(final TrainingDto dto);
}
