package com.dynonuggets.refonteimplicaction.user.controller;

import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.user.util.UserUris.*;


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

    @GetMapping(GET_PENDING_USER_URI)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllPendingUsers(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> pendingUsers = userService.getAllPendingActivationUsers(pageable);
        return ResponseEntity.ok(pendingUsers);
    }

    @PostMapping(ENABLE_USER)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> enableUser(@PathVariable final String username) {
        userService.enableUser(username);
        return ResponseEntity.ok().build();
    }
}
