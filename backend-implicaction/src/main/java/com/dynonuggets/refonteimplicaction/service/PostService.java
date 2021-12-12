package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.PostAdapter;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.Message.*;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final PostAdapter postAdapter;
    private final CommentService commentService;
    private final VoteService voteService;

    @Transactional
    public PostResponse saveOrUpdate(PostRequest postRequest) {
        if (StringUtils.isEmpty(postRequest.getName())) {
            throw new IllegalArgumentException(POST_SHOULD_HAVE_A_NAME);
        }

        Group group = groupRepository.findById(postRequest.getGroupId())
                .orElseThrow(() -> new NotFoundException(String.format(SUBREDDIT_NOT_FOUND_MESSAGE, postRequest.getGroupId())));

        Post post = postAdapter.toPost(postRequest, group, authService.getCurrentUser());
        Post save = postRepository.save(post);

        return getPostResponse(save);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

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

    public List<PostResponse> getLatestPosts(int postCount) {
        List<Post> lastPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (lastPosts.size() < postCount) {
            postCount = lastPosts.size();
        }
        lastPosts = lastPosts.subList(0, postCount);

        final List<PostResponse> returnList = new ArrayList<>();

        for (Post post : lastPosts) {
            returnList.add(postAdapter.toPostResponse(post, 0, false, false));
        }
        return returnList;
    }
}
