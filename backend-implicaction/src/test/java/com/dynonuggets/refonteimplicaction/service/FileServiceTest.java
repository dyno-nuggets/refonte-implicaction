package com.dynonuggets.refonteimplicaction.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.FILE_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_FILE_BY_KEY;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Value("${app.url}")
    String appUrl;

    FileService fileService = new FileService();

    @Test
    void build_file_uri_with_valid_object_key() {
        // given
        String fileKey = "blablabla";
        String expectedUri = appUrl + FILE_BASE_URI + GET_FILE_BY_KEY.replace("{objectKey}", fileKey);

        // when
        String actualUri = fileService.buildFileUri(fileKey);

        // then
        assertThat(actualUri).isEqualTo(expectedUri);
    }

    @Test
    void build_file_uri_with_null_object_key() {
        // when
        String actualUri = fileService.buildFileUri(null);

        // then
        assertThat(actualUri).isNull();
    }
}
