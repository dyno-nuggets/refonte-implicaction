package com.dynonuggets.refonteimplicaction.filemanagement.controller;

import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.FILES_BASE_URI;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.POST_PROFILE_AVATAR;

@RestController
@RequiredArgsConstructor
@RequestMapping(FILES_BASE_URI)
public class FileController {

    private final CloudService cloudService;

    @PostMapping(POST_PROFILE_AVATAR)
    public ResponseEntity<String> postProfileAvatar(@RequestParam final MultipartFile file, @PathVariable final String username) {
        return ResponseEntity.ok(cloudService.uploadAvatar(file, username));
    }

}
