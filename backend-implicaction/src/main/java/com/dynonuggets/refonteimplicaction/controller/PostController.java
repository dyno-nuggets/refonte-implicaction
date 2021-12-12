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

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;

@RestController
@RequestMapping(POSTS_BASE_URI)
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse post = postService.saveOrUpdate(postRequest);
        URI location = UriComponentsBuilder.fromPath(POSTS_BASE_URI + GET_POST_URI)
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).body(post);
    }

    @GetMapping(GET_POST_URI)
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_LATEST_POSTS_URI)
    public ResponseEntity<List<PostResponse>> getLatestPosts(@PathVariable int postsCount) {
        List<PostResponse> response = postService.getLatestPosts(postsCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<PostResponse> postResponses = postService.getAllPosts(pageable);

        return ResponseEntity.ok(postResponses);
    }

    @GetMapping(GET_POST_COMMENTS_URI)
    public ResponseEntity<Page<CommentDto>> getAllCommentsForPost(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<CommentDto> comments = commentService.getAllCommentsForPost(pageable, postId);

        return ResponseEntity.ok(comments);
    }
}
