package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.PostAdapter;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
import com.dynonuggets.refonteimplicaction.utils.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostAdapter postAdapter;
    private final CommentService commentService;
    private final VoteService voteService;

    @Transactional
    public PostResponse saveOrUpdate(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new NotFoundException(String.format(Message.SUBREDDIT_NOT_FOUND_MESSAGE, postRequest.getSubredditName())));
        Post post = postAdapter.toPost(postRequest, subreddit, authService.getCurrentUser());
        Post save = postRepository.save(post);
        return getPostResponse(save);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(Message.POST_NOT_FOUND_MESSAGE, postId)));
        return getPostResponse(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        final Page<Post> allPosts = postRepository.findAll(pageable);
        return allPosts.map(this::getPostResponse);
    }

    private PostResponse getPostResponse(Post post) {
        return postAdapter.toPostResponse(post, commentService.commentCount(post), voteService.isPostUpVoted(post), voteService.isPostDownVoted(post));
    }
}
