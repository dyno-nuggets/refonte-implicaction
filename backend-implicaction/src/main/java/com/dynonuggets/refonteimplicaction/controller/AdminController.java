package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.ADMIN_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.ADMIN_USER_ROLES_URI;


@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_BASE_URI)
public class AdminController {
    private final UserService userService;

    @PutMapping(ADMIN_USER_ROLES_URI)
    public ResponseEntity<UserDto> updateRoleOfUser(@RequestBody UserDto userDto) {
        UserDto userUpdate = userService.updateRoleOfUser(userDto);
        return ResponseEntity.ok(userUpdate);
    }
}
