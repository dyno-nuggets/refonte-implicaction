package com.dynonuggets.refonteimplicaction.filemanagement.controller;

import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.GET_FILE_BY_KEY;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.PUBLIC_FILES_BASE_URI;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Controller
@RequestMapping(PUBLIC_FILES_BASE_URI)
@AllArgsConstructor
public class PublicFileController {

    private final CloudService cloudService;

    @ResponseBody
    @GetMapping(value = GET_FILE_BY_KEY, produces = APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@PathVariable final String objectKey) throws IOException {
        return cloudService.getFileAsBytes(objectKey);
    }
}
