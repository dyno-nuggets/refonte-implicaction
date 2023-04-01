package com.dynonuggets.refonteimplicaction.forum.topic.controller;

import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import com.dynonuggets.refonteimplicaction.forum.topic.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.forum.topic.utils.TopicUris.GET_LATEST_TOPICS;
import static com.dynonuggets.refonteimplicaction.forum.topic.utils.TopicUris.PUBLIC_TOPICS_BASE_URI;

@RestController
@RequestMapping(PUBLIC_TOPICS_BASE_URI)
@AllArgsConstructor
public class PublicForumTopicsController {
    private final TopicService topicService;

    @GetMapping(GET_LATEST_TOPICS)
    public ResponseEntity<List<TopicDto>> getLatest(@RequestParam(value = "rows", defaultValue = "10") final int rows) {
        final List<TopicDto> topics = topicService.getLatest(rows);
        return ResponseEntity.ok(topics);
    }
}
