package com.dynonuggets.refonteimplicaction.user.adapter;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.emptyStreamIfNull;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;


@Component
@AllArgsConstructor
public class UserAdapter {

    public UserDto toDto(final UserModel model) {
        if (model == null) {
            return null;
        }

        return UserDto.builder()
                .id(model.getId())
                .firstname(model.getFirstname())
                .lastname(model.getLastname())
                .username(model.getUsername())
                .birthday(model.getBirthday())
                .email(model.getEmail())
                .registeredAt(model.getRegisteredAt())
                .activationKey(model.getActivationKey())
                .enabled(model.isEnabled())
                .emailVerified(model.isEmailVerified())
                .roles(rolesToRoleNames(model))
                .build();
    }

    public UserModel toModel(final UserDto dto) {
        if (dto == null) {
            return null;
        }

        final List<Role> roles = emptyStreamIfNull(dto.getRoles())
                .map(roleLabel -> {
                    final RoleEnum role = RoleEnum.valueOf(roleLabel);
                    return new Role(role.getId(), role.name(), emptySet());
                })
                .collect(toList());

        return UserModel.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstname(dto.getFirstname())
                .birthday(dto.getBirthday())
                .lastname(dto.getLastname())
                .activationKey(dto.getActivationKey())
                .enabled(dto.isEnabled())
                .email(dto.getEmail())
                .registeredAt(dto.getRegisteredAt())
                .emailVerified(dto.isEmailVerified())
                .roles(roles)
                .build();
    }

    public UserDto toDtoLight(final UserModel model) {
        if (model == null) {
            return null;
        }

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .roles(rolesToRoleNames(model))
                .build();
    }

    private List<String> rolesToRoleNames(final UserModel model) {
        return emptyStreamIfNull(model.getRoles())
                .map(Role::getName)
                .collect(toList());
    }
}
