package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.service.CommentService;
import com.dynonuggets.refonteimplicaction.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.*;

@RestController
@RequestMapping(POSTS_BASE_URI)
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody final PostRequest postRequest) {
        final PostResponse post = postService.saveOrUpdate(postRequest);
        final URI location = UriComponentsBuilder.fromPath(POSTS_BASE_URI + GET_POST_URI)
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).body(post);
    }

    @GetMapping(GET_POST_URI)
    public ResponseEntity<PostResponse> getPost(@PathVariable final Long postId) {
        final PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_LATEST_POSTS_URI)
    public ResponseEntity<List<PostResponse>> getLatestPosts(@PathVariable final int postsCount) {
        final List<PostResponse> response = postService.getLatestPosts(postsCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Page<PostResponse> postResponses = postService.getAllPosts(pageable);

        return ResponseEntity.ok(postResponses);
    }

    @GetMapping(GET_POST_COMMENTS_URI)
    public ResponseEntity<Page<CommentDto>> getAllCommentsForPost(
            @PathVariable final Long postId,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Page<CommentDto> comments = commentService.getAllCommentsForPost(pageable, postId);

        return ResponseEntity.ok(comments);
    }
}
