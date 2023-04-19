package com.dynonuggets.refonteimplicaction.community.relation.mapper;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.mapper.ProfileMapper;
import com.dynonuggets.refonteimplicaction.community.relation.domain.model.RelationModel;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RelationMapper {

    ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    @Mapping(target = "sender", expression = "java(toProfileDtoLight(model.getSender()))")
    @Mapping(target = "receiver", expression = "java(toProfileDtoLight(model.getReceiver()))")
    RelationsDto toDto(final RelationModel model);

    default ProfileDto toProfileDtoLight(final ProfileModel profile) {
        return profileMapper.toDtoLight(profile);
    }
}
