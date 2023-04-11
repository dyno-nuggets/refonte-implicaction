package com.dynonuggets.refonteimplicaction.filemanagement.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.filemanagement.error.FileException;
import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import com.dynonuggets.refonteimplicaction.filemanagement.model.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import com.dynonuggets.refonteimplicaction.filemanagement.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.FILE_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.utils.Message.UNKNOWN_FILE_UPLOAD_MESSAGE;
import static com.dynonuggets.refonteimplicaction.filemanagement.error.FileErrorResult.FILE_IS_TOO_LARGE;
import static com.dynonuggets.refonteimplicaction.filemanagement.error.FileErrorResult.UNAUTHORIZED_CONTENT_TYPE;
import static java.lang.String.format;
import static java.util.List.of;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.*;

@Service
@RequiredArgsConstructor
public class S3CloudServiceImpl implements CloudService {

    private static final Integer MAX_IMAGE_SIZE_IN_BIT = 40000000;
    private static final List<String> IMAGE_CONTENT_TYPES = of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);
    private final AuthService authService;
    private final AmazonS3Client client;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    @Value("${app.s3.bucket-name}")
    private String bucketName;

    @Override
    public FileModel uploadFile(final MultipartFile file, final boolean publicAccess) {
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
                    .publicAccess(publicAccess)
                    .build();
        } catch (final IOException exception) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, format(UNKNOWN_FILE_UPLOAD_MESSAGE, originalFilename));
        }
    }

    @Override
    @Transactional
    public String uploadAvatar(@NonNull final MultipartFile file, @NonNull final String username) {
        if (file.getSize() > MAX_IMAGE_SIZE_IN_BIT) {
            throw new FileException(FILE_IS_TOO_LARGE, file.getOriginalFilename());
        }

        if (!IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileException(UNAUTHORIZED_CONTENT_TYPE, file.getOriginalFilename());
        }

        authService.verifyAccessIsGranted(username);
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(username);
        final FileModel fileModel = uploadFile(file, true);
        fileRepository.save(fileModel);
        profile.setAvatar(fileModel);
        profileRepository.save(profile);

        return fileService.buildFileUri(fileModel);
    }

    @Override
    public byte[] getFileAsBytes(final String objectKey) throws IOException {
        final FileModel fileModel = fileRepository.findByObjectKeyAndPublicAccessIsTrue(objectKey)
                .orElseThrow(() -> new NotFoundException(format(FILE_NOT_FOUND_MESSAGE, objectKey)));

        final S3Object s3Object = client.getObject(new GetObjectRequest(bucketName, fileModel.getObjectKey()));
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

}
