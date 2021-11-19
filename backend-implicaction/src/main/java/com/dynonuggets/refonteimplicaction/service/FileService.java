package com.dynonuggets.refonteimplicaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.FILE_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_FILE_BY_KEY;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${app.url}")
    private String appUrl;

    public String buildFileUri(String fileKey) {
        return fileKey != null ? appUrl + FILE_BASE_URI + GET_FILE_BY_KEY.replace("{objectKey}", fileKey) : null;
    }

}
