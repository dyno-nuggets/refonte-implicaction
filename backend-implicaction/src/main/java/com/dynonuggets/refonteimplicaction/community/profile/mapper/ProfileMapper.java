package com.dynonuggets.refonteimplicaction.community.profile.mapper;

import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.mapper.GroupMapper;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.community.workexperience.mapper.WorkExperienceMapper;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Mapper
public interface ProfileMapper {

    WorkExperienceMapper workExperienceMapper = Mappers.getMapper(WorkExperienceMapper.class);
    GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "username", expression = "java(mapField(model, \"username\"))")
    @Mapping(target = "firstname", expression = "java(mapField(model, \"firstname\"))")
    @Mapping(target = "lastname", expression = "java(mapField(model, \"lastname\"))")
    @Mapping(target = "email", expression = "java(mapField(model, \"email\"))")
    @Mapping(target = "experiences", expression = "java(mapExperiencesDtos(model))")
    @Mapping(target = "groups", expression = "java(mapGroupsDtos(model))")
    @Mapping(target = "birthday", expression = "java(mapBirthday(model))")
    ProfileDto toDto(final ProfileModel model);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", expression = "java(mapField(model, \"username\"))")
    @Mapping(target = "firstname", expression = "java(mapField(model, \"firstname\"))")
    @Mapping(target = "lastname", expression = "java(mapField(model, \"lastname\"))")
    @Mapping(target = "email", expression = "java(mapField(model, \"email\"))")
    @Mapping(target = "imageUrl", source = "imageUrl")
    ProfileDto toDtoLight(final ProfileModel model);

    default String mapField(final ProfileModel model, final String fieldName) {
        final Function<UserModel, String> mapper;
        switch (fieldName) {
            case "username":
                mapper = UserModel::getUsername;
                break;
            case "firstname":
                mapper = UserModel::getFirstname;
                break;
            case "lastname":
                mapper = UserModel::getLastname;
                break;
            case "email":
                mapper = UserModel::getEmail;
                break;
            default:
                throw new IllegalArgumentException(format("Le champ %s n'existe pas", fieldName));
        }
        return callIfNotNull(model.getUser(), mapper);
    }

    default List<WorkExperienceDto> mapExperiencesDtos(final ProfileModel model) {
        return emptyStreamIfNull(model.getExperiences())
                .map(workExperienceMapper::toDto).collect(toList());
    }

    default List<GroupDto> mapGroupsDtos(final ProfileModel model) {
        return emptyStreamIfNull(model.getGroups())
                .map(groupMapper::toDto).collect(toList());
    }

    default LocalDate mapBirthday(final ProfileModel model) {
        return callIfNotNull(model.getUser(), UserModel::getBirthday);
    }
}
