package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.auth.service.UserService;
import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.*;

@RestController
@AllArgsConstructor
@RequestMapping(USER_BASE_URI)
public class UserController {

    private final UserService userService;
    private final RelationService relationService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> users = userService.getAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = GET_COMMUNITY_URI)
    public ResponseEntity<Page<UserDto>> getAllCommunity(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> users = userService.getAllCommunity(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = GET_USER_URI)
    public ResponseEntity<UserDto> getUserProfile(@PathVariable final Long userId) {
        final UserDto userdto = userService.getUserById(userId);
        return ResponseEntity.ok(userdto);
    }

    @GetMapping(path = GET_FRIEND_URI)
    public ResponseEntity<Page<UserDto>> getAllFriends(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @PathVariable("userId") final Long userId
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> userDtos = relationService.getAllFriendsByUserId(userId, pageable);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(path = GET_FRIEND_REQUESTS_SENT_URI)
    public ResponseEntity<Page<UserDto>> getSentFriendRequest(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Long userId = authService.getCurrentUser().getId();
        final Page<UserDto> usersDto = relationService.getSentFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(path = GET_FRIEND_REQUESTS_RECEIVED_URI)
    public ResponseEntity<Page<UserDto>> getReceivedFriendRequest(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Long userId = authService.getCurrentUser().getId();
        final Page<UserDto> usersDto = relationService.getReceivedFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody final UserDto userDto) {
        final UserDto userUpdate = userService.updateUser(userDto);
        return ResponseEntity.ok(userUpdate);
    }

    @GetMapping(path = GET_PENDING_USER_URI)
    public ResponseEntity<Page<UserDto>> getAllPendingUsers(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> pendingUsers = userService.getAllPendingActivationUsers(pageable);
        return ResponseEntity.ok(pendingUsers);
    }

    @PostMapping(SET_USER_IMAGE)
    public ResponseEntity<UserDto> updateImageProfile(@RequestParam("file") final MultipartFile file) {
        final UserDto userDto = userService.updateImageProfile(file);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(GET_USER_GROUPS_URI)
    public ResponseEntity<List<GroupDto>> getUserGroups(@PathVariable final Long userId) {
        final List<GroupDto> groupsDto = userService.getUserGroups(userId);
        return ResponseEntity.ok(groupsDto);
    }

}
