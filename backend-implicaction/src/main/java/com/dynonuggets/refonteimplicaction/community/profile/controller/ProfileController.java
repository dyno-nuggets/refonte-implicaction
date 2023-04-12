package com.dynonuggets.refonteimplicaction.community.profile.controller;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.GET_PROFILE_BY_USERNAME;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.PROFILES_BASE_URI;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.POST_PROFILE_AVATAR;
import static org.springframework.data.domain.PageRequest.of;

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
        return ResponseEntity.ok(profileService.uploadAvatar(file, username));
    }
}
