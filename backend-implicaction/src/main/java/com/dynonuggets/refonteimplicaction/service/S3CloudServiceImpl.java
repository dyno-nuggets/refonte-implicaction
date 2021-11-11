package com.dynonuggets.refonteimplicaction.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.utils.Message.UNKNOWN_FILE_UPLOAD_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
public class S3CloudServiceImpl implements CloudService {

    public static final String BUCKET_NAME = "implicaction";

    private final AmazonS3Client client;

    @Override
    public FileModel uploadFile(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        final String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        final String key = UUID.randomUUID() + filenameExtension;
        final ObjectMetadata metadata = new ObjectMetadata();
        final long fileSize = file.getSize();
        final String contentType = file.getContentType();

        metadata.setContentLength(fileSize);
        metadata.setContentType(contentType);

        try {
            client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
            client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);
            return FileModel.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(contentType)
                    .url(client.getResourceUrl(BUCKET_NAME, key))
                    .build();
        } catch (IOException exception) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, String.format(UNKNOWN_FILE_UPLOAD_MESSAGE, originalFilename));
        }
    }

}
