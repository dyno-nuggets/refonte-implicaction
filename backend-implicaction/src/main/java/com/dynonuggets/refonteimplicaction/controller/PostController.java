package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_POST_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.POST_BASE_URI;

@RestController
@RequestMapping(POST_BASE_URI)
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse post = postService.save(postRequest);
        URI location = UriComponentsBuilder.fromPath(POST_BASE_URI + GET_POST_URI)
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping(GET_POST_URI)
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }
}