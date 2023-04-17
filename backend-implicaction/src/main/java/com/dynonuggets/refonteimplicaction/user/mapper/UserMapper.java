package com.dynonuggets.refonteimplicaction.user.mapper;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.util.stream.Collectors.toSet;

@Mapper
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(rolesToRoleNames(model.getRoles()))")
    UserDto toDto(UserModel model);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "roles", expression = "java(rolesToRoleNames(model.getRoles()))")
    UserDto toDtoLight(final UserModel model);

    default Set<String> rolesToRoleNames(final Set<RoleModel> roles) {
        return emptyStreamIfNull(roles)
                .map(RoleModel::getName)
                .collect(toSet());
    }
}
