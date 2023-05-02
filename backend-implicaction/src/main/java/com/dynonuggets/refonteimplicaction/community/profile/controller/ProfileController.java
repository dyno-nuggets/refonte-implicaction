package com.dynonuggets.refonteimplicaction.community.profile.controller;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.GET_PROFILE_BY_USERNAME;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.PROFILES_BASE_URI;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.POST_PROFILE_AVATAR;

@RestController
@AllArgsConstructor
@RequestMapping(PROFILES_BASE_URI)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<ProfileDto>> getAllProfiles(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder,
            @RequestParam(value = "relationCriteria", defaultValue = "ANY") final RelationCriteriaEnum relationCriteria
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        return ResponseEntity.ok(profileService.getAllProfiles(relationCriteria, pageable));
    }

    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody final ProfileUpdateRequest updateRequest) {
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
