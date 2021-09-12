package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import com.dynonuggets.refonteimplicaction.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RelationService relationService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAll(pageable);
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
        Page<UserDto> userDtos = relationService.getAllFriendsByUserId(pageable, userId);
        return ResponseEntity.ok(userDtos);
    }
}
