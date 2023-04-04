package com.dynonuggets.refonteimplicaction.filemanagement.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import com.dynonuggets.refonteimplicaction.filemanagement.model.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.*;
import static java.lang.String.format;
import static java.util.List.of;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.*;

@Service
@RequiredArgsConstructor
public class S3CloudServiceImpl implements CloudService {

    private static final Integer MAX_IMAGE_SIZE_IN_BIT = 40000000;
    private static final List<String> IMAGE_CONTENT_TYPES = of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);
    private final AmazonS3Client client;
    private final FileRepository fileRepository;
    @Value("${app.s3.bucket-name}")
    private String bucketName;

    @Override
    public FileModel uploadFile(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        final String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        final String key = UUID.randomUUID() + "." + filenameExtension;
        final ObjectMetadata metadata = new ObjectMetadata();
        final long fileSize = file.getSize();
        final String contentType = file.getContentType();

        metadata.setContentLength(fileSize);
        metadata.setContentType(contentType);

        try {
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
            }

            client.putObject(bucketName, key, file.getInputStream(), metadata);
            client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            return FileModel.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(contentType)
                    .url(client.getResourceUrl(bucketName, key))
                    .objectKey(key)
                    .build();
        } catch (final IOException exception) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, format(UNKNOWN_FILE_UPLOAD_MESSAGE, originalFilename));
        }
    }

    @Override
    public FileModel uploadImage(final MultipartFile file) {
        if (file.getSize() > MAX_IMAGE_SIZE_IN_BIT) {
            throw new RuntimeException(format(FILE_SIZE_TOO_LARGE_MESSAGE, file.getOriginalFilename(), MAX_IMAGE_SIZE_IN_BIT));
        }

        if (!IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
            throw new RuntimeException(format(UNAUTHORIZED_CONTENT_TYPE_MESSAGE, file.getOriginalFilename()));
        }

        final FileModel fileModel = uploadFile(file);
        return fileRepository.save(fileModel);
    }

    @Override
    public byte[] getFileAsBytes(final String objectKey) throws IOException {
        final FileModel fileModel = fileRepository.findByObjectKeyAndPublicAccessIsTrue(objectKey)
                .orElseThrow(() -> new NotFoundException(format(FILE_NOT_FOUND_MESSAGE, objectKey)));

        final S3Object s3Object = client.getObject(new GetObjectRequest(bucketName, fileModel.getObjectKey()));
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

}
