package com.dynonuggets.refonteimplicaction.auth.rest.controller;

import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import com.dynonuggets.refonteimplicaction.auth.service.UserService;
import com.dynonuggets.refonteimplicaction.community.rest.dto.GroupDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.*;

@RestController
@AllArgsConstructor
@RequestMapping(USER_BASE_URI)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> users = userService.getAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(GET_COMMUNITY_URI)
    public ResponseEntity<Page<UserDto>> getAllCommunity(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> users = userService.getAllCommunity(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(GET_USER_URI)
    public ResponseEntity<UserDto> getUserProfile(@PathVariable final Long userId) {
        final UserDto userdto = userService.getUserById(userId);
        return ResponseEntity.ok(userdto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody final UserDto userDto) {
        final UserDto userUpdate = userService.updateUser(userDto);
        return ResponseEntity.ok(userUpdate);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(GET_PENDING_USER_URI)
    public ResponseEntity<Page<UserDto>> getAllPendingUsers(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> pendingUsers = userService.getAllPendingActivationUsers(pageable);
        return ResponseEntity.ok(pendingUsers);
    }

    @PostMapping(SET_USER_IMAGE)
    public ResponseEntity<UserDto> updateImageProfile(@RequestParam final MultipartFile file) {
        final UserDto userDto = userService.updateImageProfile(file);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(GET_USER_GROUPS_URI)
    public ResponseEntity<List<GroupDto>> getUserGroups(@PathVariable final Long userId) {
        final List<GroupDto> groupsDto = userService.getUserGroups(userId);
        return ResponseEntity.ok(groupsDto);
    }

}
