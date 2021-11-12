package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.service.CloudService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.FILE_BASE_URI;

@RestController
@RequestMapping(FILE_BASE_URI)
@AllArgsConstructor
public class FileController {

    private final CloudService cloudService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        FileModel fileModel = cloudService.uploadImage(file);
        URI location = new URI(fileModel.getUrl());
        return ResponseEntity.created(location).body(fileModel.getUrl());
    }
}
