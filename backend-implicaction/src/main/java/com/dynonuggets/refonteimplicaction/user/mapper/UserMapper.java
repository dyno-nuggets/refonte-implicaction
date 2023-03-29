package com.dynonuggets.refonteimplicaction.user.mapper;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.Utils.emptyStreamIfNull;
import static java.util.stream.Collectors.toList;

@Mapper
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(rolesToRoleNames(model.getRoles()))")
    UserDto toDto(UserModel model);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "roles", expression = "java(rolesToRoleNames(model.getRoles()))")
    UserDto toDtoLight(final UserModel model);

    default List<String> rolesToRoleNames(final List<Role> roles) {
        return emptyStreamIfNull(roles)
                .map(Role::getName)
                .collect(toList());
    }
}
