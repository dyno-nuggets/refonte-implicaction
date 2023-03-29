package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CommentAdapter;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.repository.CommentRepository;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.COMMENT_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.utils.Message.POST_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentAdapter commentAdapter;

    public int commentCount(final Post post) {
        return commentRepository.countAllByPost_Id(post.getId());
    }

    @Transactional
    public CommentDto saveOrUpdate(final CommentDto commentDto) {
        final Long postId = commentDto.getPostId();
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        final UserModel currentUser = authService.getCurrentUser();
        final Comment comment = commentAdapter.toModel(commentDto, post, currentUser);
        comment.setCreatedAt(Instant.now());

        final Comment save = commentRepository.save(comment);
        return commentAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(final long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format(COMMENT_NOT_FOUND, commentId)));

        return commentAdapter.toDto(comment);
    }

    public Page<CommentDto> getAllCommentsForPost(final Pageable pageable, final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        final Page<Comment> comments = commentRepository.findByPostOrderById(post, pageable);

        return comments.map(commentAdapter::toDto);
    }
}
