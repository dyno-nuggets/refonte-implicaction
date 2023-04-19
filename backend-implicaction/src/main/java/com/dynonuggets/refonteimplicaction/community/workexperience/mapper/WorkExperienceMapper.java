package com.dynonuggets.refonteimplicaction.community.workexperience.mapper;

import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WorkExperienceMapper {

    @Mapping(target = "profile", ignore = true)
    WorkExperienceDto toDto(final WorkExperienceModel model);

    @Mapping(target = "profile", ignore = true)
    WorkExperienceModel toModel(final WorkExperienceDto dto);
}
