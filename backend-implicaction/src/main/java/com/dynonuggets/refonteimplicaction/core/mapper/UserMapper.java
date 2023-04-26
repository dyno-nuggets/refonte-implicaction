package com.dynonuggets.refonteimplicaction.core.mapper;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.util.stream.Collectors.toSet;

@Mapper
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(rolesToRoleEnum(model.getRoles()))")
    UserDto toDto(UserModel model);

    default Set<RoleEnum> rolesToRoleEnum(final Set<RoleModel> roles) {
        return emptyStreamIfNull(roles)
                .map(RoleModel::getName)
                .collect(toSet());
    }
}
