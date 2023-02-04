package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.COMMENTS_BASE_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.GET_COMMENT_URI;

@RestController
@AllArgsConstructor
@RequestMapping(COMMENTS_BASE_URI)
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody final CommentDto commentDto) {
        final CommentDto save = commentService.saveOrUpdate(commentDto);
        final URI location = UriComponentsBuilder.fromPath(COMMENTS_BASE_URI + GET_COMMENT_URI)
                .buildAndExpand(save.getId())
                .toUri();
        return ResponseEntity.created(location).body(save);
    }

    @GetMapping(GET_COMMENT_URI)
    public ResponseEntity<CommentDto> getComment(@PathVariable final long commentId) {
        final CommentDto commentDto = commentService.getComment(commentId);
        return ResponseEntity.ok(commentDto);
    }
}
