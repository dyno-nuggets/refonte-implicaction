package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CommentAdapter;
import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.CommentRepository;
import com.dynonuggets.refonteimplicaction.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.utils.Message.*;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final GroupRepository groupRepository;

    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentAdapter commentAdapter;

    public int commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    @Transactional
    public CommentDto saveOrUpdate(CommentDto commentDto) {
        final Long postId = commentDto.getPostId();
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        final Long groupId = commentDto.getGroupId();
        final Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format(GROUP_NOT_FOUND_MESSAGE, groupId)));

        final User currentUser = authService.getCurrentUser();
        final Comment comment = commentAdapter.toModel(commentDto, post, currentUser, group);
        comment.setCreatedAt(Instant.now());

        final Comment save = commentRepository.save(comment);
        return commentAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format(COMMENT_NOT_FOUND, commentId)));

        return commentAdapter.toDto(comment);
    }

    public Page<CommentDto> getAllCommentsForPost(Pageable pageable, Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        final Page<Comment> comments = commentRepository.findByPostOrderById(post, pageable);

        return comments.map(commentAdapter::toDto);
    }
}
