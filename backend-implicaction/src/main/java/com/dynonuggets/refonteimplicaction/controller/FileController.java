package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.service.CloudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.FILE_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_FILE_BY_KEY;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Controller
@RequestMapping(FILE_BASE_URI)
@AllArgsConstructor
public class FileController {

    private final CloudService cloudService;

    @GetMapping(value = GET_FILE_BY_KEY, produces = APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getFile(@PathVariable final String objectKey) throws IOException {
        return cloudService.getFileAsBytes(objectKey);
    }
}
