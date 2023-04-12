package com.dynonuggets.refonteimplicaction.filemanagement.service;

import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {

    FileModel uploadFile(MultipartFile file, boolean publicAccess) throws IOException;

    String uploadPublicImage(MultipartFile file);

    byte[] getFileAsBytes(String objectKey) throws IOException;
}
