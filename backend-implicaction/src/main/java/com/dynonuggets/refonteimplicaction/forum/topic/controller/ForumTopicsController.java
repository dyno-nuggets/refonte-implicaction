package com.dynonuggets.refonteimplicaction.forum.topic.controller;

import com.dynonuggets.refonteimplicaction.forum.response.dto.ResponseDto;
import com.dynonuggets.refonteimplicaction.forum.response.service.ResponseService;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.CreateTopicRequest;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.UpdateTopicRequest;
import com.dynonuggets.refonteimplicaction.forum.topic.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.forum.topic.utils.TopicUris.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(TOPIC_BASE_URI)
@AllArgsConstructor
public class ForumTopicsController {
    private final TopicService topicService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@RequestBody final CreateTopicRequest topicDto) {
        final TopicDto saveDto = topicService.createTopic(topicDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @PatchMapping
    public ResponseEntity<TopicDto> updateTopic(@RequestBody final UpdateTopicRequest topicDto) {
        final TopicDto saveDto = topicService.updateTopic(topicDto);
        return ResponseEntity.ok(saveDto);
    }

    @GetMapping(GET_TOPIC_URI)
    public ResponseEntity<TopicDto> getTopic(@PathVariable final long topicId) {
        final TopicDto foundDto = topicService.getTopic(topicId);
        return ResponseEntity.ok(foundDto);
    }

    @GetMapping(GET_LATEST_TOPICS)
    public ResponseEntity<List<TopicDto>> getLatest(@RequestParam(value = "rows", defaultValue = "10") final int rows) {
        final List<TopicDto> topics = topicService.getLatest(rows);
        return ResponseEntity.ok(topics);
    }

    @GetMapping(GET_RESPONSE_FROM_TOPIC_URI)
    public ResponseEntity<Page<ResponseDto>> getResponsesFromTopic(
            @PathVariable final long topicId,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Page<ResponseDto> responseDtos = responseService.getResponsesFromTopic(topicId, pageable);
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping(DELETE_TOPIC_URI)
    public ResponseEntity<Void> delete(@PathVariable final Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

}
