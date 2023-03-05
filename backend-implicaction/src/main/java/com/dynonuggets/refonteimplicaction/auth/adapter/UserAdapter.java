package com.dynonuggets.refonteimplicaction.auth.adapter;

import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.emptyStreamIfNull;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;


@Component
@AllArgsConstructor
public class UserAdapter {

    public UserDto toDto(final User model) {
        if (model == null) {
            return null;
        }

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .email(model.getEmail())
                .registeredAt(model.getRegisteredAt())
                .activationKey(model.getActivationKey())
                .active(model.isActive())
                .roles(rolesToRoleNames(model))
                .build();
    }

    public User toModel(final UserDto dto) {
        if (dto == null) {
            return null;
        }

        final List<Role> roles = emptyStreamIfNull(dto.getRoles())
                .map(roleLabel -> {
                    final RoleEnum role = RoleEnum.valueOf(roleLabel);
                    return new Role(role.getId(), role.name(), emptySet());
                })
                .collect(toList());

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .activationKey(dto.getActivationKey())
                .active(dto.isActive())
                .email(dto.getEmail())
                .registeredAt(dto.getRegisteredAt())
                .roles(roles)
                .build();
    }

    public UserDto toDtoLight(final User model) {
        if (model == null) {
            return null;
        }

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .roles(rolesToRoleNames(model))
                .build();
    }

    private List<String> rolesToRoleNames(final User model) {
        return emptyStreamIfNull(model.getRoles())
                .map(Role::getName)
                .collect(toList());
    }
}
