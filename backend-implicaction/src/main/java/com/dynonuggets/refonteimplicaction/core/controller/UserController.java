package com.dynonuggets.refonteimplicaction.core.controller;

import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import com.dynonuggets.refonteimplicaction.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.dynonuggets.refonteimplicaction.core.utils.UserUris.*;


@RestController
@AllArgsConstructor
@RequestMapping(USER_BASE_URI)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Page<UserDto> users = userService.getAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(GET_TOTAL_USERS)
    public ResponseEntity<Long> getTotalUsers() {
        return ResponseEntity.ok(userService.getTotalUsers());
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
    public ResponseEntity<UserDto> enableUser(@PathVariable final String username) {
        return ResponseEntity.ok(userService.enableUser(username));
    }

    @PostMapping(UPDATE_USER_ROLES)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUserRoles(@PathVariable final String username, @RequestBody final Set<RoleEnum> roles) {
        return ResponseEntity.ok(userService.updateUserRoles(username, roles));
    }
}
