package com.dynonuggets.refonteimplicaction.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.FILE_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_FILE_BY_KEY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileController.class)
class FileControllerIntegrationTest extends ControllerIntegrationTestBase {

    @MockBean
    AmazonS3Client client;

    @MockBean
    FileRepository fileRepository;

    @MockBean
    S3CloudServiceImpl cloudService;

    @Test
    @WithMockUser
    void should_return_file_when_file_exists_and_authenticated() throws Exception {
        // given
        String objectKey = "blablabla";
        byte[] expectedBytes = "Je suis un tableau d'octets on dirait pas mais si !".getBytes();
        given(cloudService.getFileAsBytes(anyString())).willReturn(expectedBytes);

        // when
        final ResultActions resultActions = mvc.perform(
                get(FILE_BASE_URI + GET_FILE_BY_KEY, objectKey)
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(expectedBytes));


        verify(cloudService, times(1)).getFileAsBytes(anyString());
    }

    @Test
    void should_return_forbidden_when_getting_file_and_not_authenticated() throws Exception {
        mvc.perform(get(FILE_BASE_URI + GET_FILE_BY_KEY, "blabla").contentType(APPLICATION_JSON)).andDo(print())
                .andExpect(status().isForbidden());

        verify(cloudService, never()).getFileAsBytes(anyString());
    }
}
