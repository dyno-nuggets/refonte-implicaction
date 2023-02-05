package com.dynonuggets.refonteimplicaction.controller.forum;

import com.dynonuggets.refonteimplicaction.dto.forum.CreateTopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.ResponseDto;
import com.dynonuggets.refonteimplicaction.dto.forum.TopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.UpdateTopicDto;
import com.dynonuggets.refonteimplicaction.service.forum.ResponseService;
import com.dynonuggets.refonteimplicaction.service.forum.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(TOPIC_BASE_URI)
@AllArgsConstructor
public class ForumTopicsController {
    private final TopicService topicService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@RequestBody CreateTopicDto topicDto) {
        TopicDto saveDto = topicService.createTopic(topicDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @PatchMapping
    public ResponseEntity<TopicDto> updateTopic(@RequestBody UpdateTopicDto topicDto) {
        TopicDto saveDto = topicService.updateTopic(topicDto);
        return ResponseEntity.ok(saveDto);
    }

    @GetMapping(GET_TOPIC_URI)
    public ResponseEntity<TopicDto> getTopic(@PathVariable long topicId) {
        TopicDto foundDto = topicService.getTopic(topicId);
        return ResponseEntity.ok(foundDto);
    }

    @GetMapping(GET_LATEST_TOPICS)
    public ResponseEntity<List<TopicDto>> getLatest(@PathVariable int topicCount) {
        List<TopicDto> topics = topicService.getLatest(topicCount);
        return ResponseEntity.ok(topics);
    }

    @GetMapping(GET_RESPONSE_FROM_TOPIC_URI)
    public ResponseEntity<Page<ResponseDto>> getResponsesFromTopic(
            @PathVariable long topicId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<ResponseDto> responseDtos = responseService.getResponsesFromTopic(topicId, pageable);
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping(GET_TOPIC_URI)
    public ResponseEntity<Void> delete(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

}
