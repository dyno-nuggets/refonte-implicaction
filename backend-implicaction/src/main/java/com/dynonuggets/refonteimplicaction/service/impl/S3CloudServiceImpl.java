package com.dynonuggets.refonteimplicaction.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.CloudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.utils.Message.*;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
public class S3CloudServiceImpl implements CloudService {

    private static final Integer MAX_IMAGE_SIZE_IN_BIT = 40000000;
    private static final String BUCKET_NAME = "refonte-implicaction";
    private static final List<String> IMAGE_CONTENT_TYPES = asList("image/jpeg", "image/png");
    private final AmazonS3Client client;
    private final FileRepository fileRepository;

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
            if (!client.doesBucketExist(BUCKET_NAME)) {
                client.createBucket(BUCKET_NAME);
            }
            client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
            client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);
            return FileModel.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(contentType)
                    .url(client.getResourceUrl(BUCKET_NAME, key))
                    .objectKey(key)
                    .build();
        } catch (IOException exception) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, String.format(UNKNOWN_FILE_UPLOAD_MESSAGE, originalFilename));
        }
    }

    @Override
    public FileModel uploadImage(MultipartFile file) {
        if (file.getSize() > MAX_IMAGE_SIZE_IN_BIT) {
            throw new ImplicactionException(String.format(FILE_SIZE_TOO_LARGE_MESSAGE, file.getOriginalFilename(), MAX_IMAGE_SIZE_IN_BIT));
        }
        if (!IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
            throw new ImplicactionException(String.format(UNAUTHORIZED_CONTENT_TYPE_MESSAGE, file.getOriginalFilename()));
        }
        return uploadFile(file);
    }

    @Override
    public byte[] getFileAsBytes(String objectKey) throws IOException {
        final FileModel fileModel = fileRepository.findByObjectKey(objectKey)
                .orElseThrow(() -> new NotFoundException(String.format(FILE_NOT_FOUND_MESSAGE, objectKey)));

        final S3Object fileObject = client.getObject(new GetObjectRequest(BUCKET_NAME, fileModel.getObjectKey()));
        InputStream in = new BufferedInputStream(fileObject.getObjectContent());
        return IOUtils.toByteArray(in);
    }

}
