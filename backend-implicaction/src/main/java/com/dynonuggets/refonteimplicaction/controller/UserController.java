package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RelationService relationService;
    private final AuthService authService;
    private final TrainingService trainingService;
    private final WorkExperienceService workExperienceService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAll(pageable, true);
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

    @PutMapping(path = "/{userId}/trainings")
    public ResponseEntity<List<TrainingDto>> updateAllTrainings(@RequestBody List<TrainingDto> trainingDtos, @PathVariable Long userId) {
        List<TrainingDto> trainingUpdates = trainingService.updateByUserId(trainingDtos, userId);
        return ResponseEntity.ok(trainingUpdates);
    }

    @PutMapping(path = "/{userId}/experiences")
    public ResponseEntity<List<WorkExperienceDto>> updateALlExperiences(@RequestBody List<WorkExperienceDto> workExperienceDtos, @PathVariable Long userId) {
        List<WorkExperienceDto> experienceUpdates = workExperienceService.updateByUserId(workExperienceDtos, userId);
        return ResponseEntity.ok(experienceUpdates);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto userUpdate = userService.updateByUserId(userDto, userId);
        return ResponseEntity.ok(userUpdate);
    }
}
