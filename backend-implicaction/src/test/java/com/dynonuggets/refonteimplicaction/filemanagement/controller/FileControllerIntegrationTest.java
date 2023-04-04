package com.dynonuggets.refonteimplicaction.filemanagement.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.filemanagement.model.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.GET_FILE_BY_KEY;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.PUBLIC_FILE_BASE_URI;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PublicFileController.class)
@TestPropertySource("/application-test.yml")
class FileControllerIntegrationTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = PUBLIC_FILE_BASE_URI;

    @MockBean
    AmazonS3Client client;

    @MockBean
    FileRepository fileRepository;

    @MockBean
    CloudService cloudService;

    @Test
    @WithMockUser
    void should_return_file_when_file_exists_and_authenticated() throws Exception {
        should_response_ok_with_file_when_exists();
    }

    @Test
    void should_return_forbidden_when_getting_file_and_not_authenticated() throws Exception {
        should_response_ok_with_file_when_exists();
    }
    
    private void should_response_ok_with_file_when_exists() throws Exception {
        // given
        final String objectKey = "blablabla";
        final byte[] expectedBytes = "Je suis un tableau d'octets on dirait pas mais si !".getBytes();
        given(cloudService.getFileAsBytes(objectKey)).willReturn(expectedBytes);

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_FILE_BY_KEY), objectKey)
                .accept(APPLICATION_OCTET_STREAM));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(expectedBytes));
        verify(cloudService, times(1)).getFileAsBytes(objectKey);
    }
}
