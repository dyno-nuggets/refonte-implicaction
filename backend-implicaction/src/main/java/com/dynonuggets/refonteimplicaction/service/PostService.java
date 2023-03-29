package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.PostAdapter;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.group.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.notification.service.NotificationService;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final PostAdapter postAdapter;
    private final CommentService commentService;
    private final VoteService voteService;
    private final NotificationService notificationService;

    @Transactional
    public PostResponse saveOrUpdate(final PostRequest postRequest) {
        if (StringUtils.isEmpty(postRequest.getName())) {
            throw new IllegalArgumentException(POST_SHOULD_HAVE_A_NAME);
        }

        final GroupModel group = groupRepository.findById(postRequest.getGroupId())
                .orElseThrow(() -> new NotFoundException(String.format(SUBREDDIT_NOT_FOUND_MESSAGE, postRequest.getGroupId())));

        final Post post = postAdapter.toPost(postRequest, group, authService.getCurrentUser());
        final Post save = postRepository.save(post);
        return getPostResponse(save);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        return getPostResponse(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(final Pageable pageable) {
        final Page<Post> allPosts = postRepository.findAll(pageable);
        return allPosts.map(this::getPostResponse);
    }

    private PostResponse getPostResponse(final Post post) {
        return postAdapter.toPostResponse(post, commentService.commentCount(post), voteService.isPostUpVoted(post), voteService.isPostDownVoted(post));
    }

    public List<PostResponse> getLatestPosts(final int postsCount) {
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, postsCount))
                .map(post -> postAdapter.toPostResponse(post, commentService.commentCount(post), voteService.isPostUpVoted(post), voteService.isPostDownVoted(post)))
                .getContent();
    }
}
