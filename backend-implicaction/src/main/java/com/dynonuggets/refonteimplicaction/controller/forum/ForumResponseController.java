package com.dynonuggets.refonteimplicaction.controller.forum;

import com.dynonuggets.refonteimplicaction.dto.forum.ResponseDto;
import com.dynonuggets.refonteimplicaction.service.forum.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.RESPONSE_BASE_URI;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(RESPONSE_BASE_URI)

@AllArgsConstructor
public class ForumResponseController {
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> createResponse(@RequestBody ResponseDto responseDto) {
        ResponseDto saveDto = responseService.createResponse(responseDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long responseId) {
        responseService.deleteResponse(responseId);
        return ResponseEntity.noContent().build();
    }
}
