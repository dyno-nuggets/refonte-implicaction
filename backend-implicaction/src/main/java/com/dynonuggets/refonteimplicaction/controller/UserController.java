package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import com.dynonuggets.refonteimplicaction.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RelationService relationService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/community")
    public ResponseEntity<Page<UserDto>> getAllCommunity(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAllCommunity(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") Long userId) {
        UserDto userdto = userService.getUserById(userId);
        return ResponseEntity.ok(userdto);
    }

    @GetMapping(path = "/{userId}/friends", params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getAllFriends(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "0") int size,
            @PathVariable("userId") Long userId
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> userDtos = relationService.getAllFriendsByUserId(userId, pageable);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(path = "/friends/sent", params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getSentFriendRequest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long userId = authService.getCurrentUser().getId();
        Page<UserDto> usersDto = relationService.getSentFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(path = "/friends/received", params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getReceivedFriendRequest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long userId = authService.getCurrentUser().getId();
        Page<UserDto> usersDto = relationService.getReceivedFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto userUpdate = userService.updateByUserId(userDto);
        return ResponseEntity.ok(userUpdate);
    }

    @GetMapping(path = "/pending", params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getAllPendingUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> pendingUsers = userService.getAllPendingActivationUsers(pageable);
        return ResponseEntity.ok(pendingUsers);
    }
}
