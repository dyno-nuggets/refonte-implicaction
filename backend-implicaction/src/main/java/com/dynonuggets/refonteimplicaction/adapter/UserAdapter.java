package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nicename(user.getNicename())
                .email(user.getEmail())
                .url(user.getUrl())
                .registered(user.getRegistered())
                .status(user.getStatus())
                .dispayName(user.getDispayName())
                .build();
    }
}
