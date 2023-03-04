package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.model.FileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.FILE_BASE_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.GET_FILE_BY_KEY;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${app.url}")
    private String appUrl;

    public String buildFileUri(final FileModel file) {
        if (file == null || file.getObjectKey() == null) {
            return null;
        }

        return appUrl + FILE_BASE_URI + GET_FILE_BY_KEY.replace("{objectKey}", file.getObjectKey());
    }

}
