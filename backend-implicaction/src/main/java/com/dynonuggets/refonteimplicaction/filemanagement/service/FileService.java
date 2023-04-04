package com.dynonuggets.refonteimplicaction.filemanagement.service;

import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import com.dynonuggets.refonteimplicaction.filemanagement.model.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.GET_FILE_BY_KEY;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.PUBLIC_FILE_BASE_URI;


@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${app.url}")
    private String appUrl;

    private final FileRepository fileRepository;

    public String save(final FileModel file) {
        fileRepository.save(file);
        return buildFileUri(file);
    }

    public String buildFileUri(final FileModel file) {
        if (file == null || file.getObjectKey() == null) {
            return null;
        }

        return appUrl + PUBLIC_FILE_BASE_URI + GET_FILE_BY_KEY.replace("{objectKey}", file.getObjectKey());
    }

}
