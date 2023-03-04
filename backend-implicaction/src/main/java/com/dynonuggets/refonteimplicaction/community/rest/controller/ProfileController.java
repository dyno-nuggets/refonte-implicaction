package com.dynonuggets.refonteimplicaction.community.rest.controller;

import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.community.util.ProfileUris.*;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PROFILES_BASE_URI)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<Page<ProfileDto>> getAllProfiles(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        return ResponseEntity.ok(profileService.getAllProfiles(of(page, rows)));
    }

    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody final ProfileUpdateRequest updateRequest) {
        return ResponseEntity.ok(profileService.updateProfile(updateRequest));
    }

    @GetMapping(GET_PROFILE_BY_USERNAME)
    public ResponseEntity<ProfileDto> getProfileByUsername(@PathVariable final String username) {
        return ResponseEntity.ok(profileService.getByUsername(username));
    }

    @PostMapping(POST_PROFILE_AVATAR)
    public ResponseEntity<ProfileDto> postProfileAvatar(@RequestParam final MultipartFile file, @PathVariable final String username) {
        return ResponseEntity.ok(profileService.updateAvatar(file, username));
    }
}
