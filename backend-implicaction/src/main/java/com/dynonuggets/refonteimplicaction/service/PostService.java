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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public PostResponse save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new NotFoundException(String.format(Message.SUBREDDIT_NOT_FOUND_MESSAGE, postRequest.getSubredditName())));
        Post post = postAdapter.toPost(postRequest, subreddit, authService.getCurrentUser());
        Post save = postRepository.save(post);
        return postAdapter.toPostResponse(save, commentService.commentCount(save), voteService.isPostUpVoted(save), voteService.isPostDownVoted(save));
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(Message.POST_NOT_FOUND_MESSAGE, postId)));
        return postAdapter.toPostResponse(post, commentService.commentCount(post), voteService.isPostUpVoted(post), voteService.isPostDownVoted(post));
    }
}
