package com.dynonuggets.refonteimplicaction.user.mapper;

import com.dynonuggets.refonteimplicaction.user.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum;
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
