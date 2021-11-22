package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.ApplicationRequest;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.APPLY_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_APPLY_URI;

@RestController
@RequestMapping(APPLY_BASE_URI)
@AllArgsConstructor
public class JobApplicationController {

    private final JobApplicationService applyService;

    @PostMapping
    public ResponseEntity<JobApplicationDto> createApply(@RequestBody ApplicationRequest requestDto) throws ImplicactionException {
        final JobApplicationDto saveDto = applyService.createApplyIfNotExists(requestDto);
        URI location = UriComponentsBuilder.fromPath(APPLY_BASE_URI + GET_APPLY_URI)
                .buildAndExpand(saveDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(saveDto);
    }
}
