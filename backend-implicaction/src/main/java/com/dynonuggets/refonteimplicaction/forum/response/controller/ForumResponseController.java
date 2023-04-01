package com.dynonuggets.refonteimplicaction.forum.response.controller;

import com.dynonuggets.refonteimplicaction.forum.response.dto.ResponseDto;
import com.dynonuggets.refonteimplicaction.forum.response.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.forum.response.utils.ResponseUris.RESPONSE_BASE_URI;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(RESPONSE_BASE_URI)
public class ForumResponseController {
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> createResponse(@RequestBody final ResponseDto responseDto) {
        final ResponseDto saveDto = responseService.createResponse(responseDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @DeleteMapping
    // TODO: /!\ il manque le responseId dans l'adresse du endpoint
    public ResponseEntity<Void> delete(@PathVariable final Long responseId) {
        responseService.deleteResponse(responseId);
        return ResponseEntity.noContent().build();
    }
}
