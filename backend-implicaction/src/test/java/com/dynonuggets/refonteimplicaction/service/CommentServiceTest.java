package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CommentAdapter;
import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.CommentRepository;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.utils.Message.COMMENT_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.utils.Message.POST_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentAdapter commentAdapter;

    @Mock
    PostRepository postRepository;

    @Mock
    AuthService authService;

    @InjectMocks
    CommentService commentService;

    @Captor
    private ArgumentCaptor<Comment> argumentCaptor;

    @Test
    void should_return_post_count() {
        // given
        Post post = new Post();
        final List<Comment> expectedComments = Arrays.asList(new Comment(), new Comment(), new Comment(), new Comment());
        given(commentRepository.findByPost(any())).willReturn(expectedComments);

        // when
        final int actualCount = commentService.commentCount(post);

        // then
        assertThat(actualCount).isEqualTo(expectedComments.size());
    }

    @Test
    void should_save_comment_when_post_exists() {
        // given
        Post post = Post.builder().id(789L).build();
        User user = User.builder().id(666L).username("lucifer").build();
        CommentDto dtoToSave = new CommentDto(null, post.getId(), Instant.now(), "coucou", "lucifer");
        Comment commentTosave = new Comment(123L, "coucou", post, dtoToSave.getCreatedAt(), user);
        CommentDto expectedDto = commentAdapter.toDto(commentTosave);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(authService.getCurrentUser()).willReturn(user);
        given(commentAdapter.toModel(any(), any(), any())).willReturn(commentTosave);
        given(commentRepository.save(any())).willReturn(commentTosave);
        given(commentAdapter.toDto(any())).willReturn(expectedDto);

        // when
        final CommentDto actualDto = commentService.saveOrUpdate(dtoToSave);

        // then
        verify(commentRepository, times(1)).save(argumentCaptor.capture());
        final Comment actual = argumentCaptor.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(commentTosave);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void should_thorw_exception_on_save_comment_when_post_not_exists() {
        // given
        long postId = 123L;
        CommentDto commentDto = CommentDto.builder().postId(postId).build();
        NotFoundException expectedException = new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, 123L));
        given(postRepository.findById(postId)).willThrow(expectedException);

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> commentService.saveOrUpdate(commentDto));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_return_post_when_exists() {
        // given
        User user = User.builder().id(666L).username("lucifer").build();
        Post post = Post.builder().id(789L).build();
        Comment comment = new Comment(123L, "coucou", post, Instant.now(), user);
        CommentDto expectedDto = commentAdapter.toDto(comment);
        given(commentRepository.findById(anyLong())).willReturn(java.util.Optional.of(comment));
        given(commentAdapter.toDto(any())).willReturn(expectedDto);

        // when
        final CommentDto actualDto = commentService.getComment(123L);

        // then
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void should_throw_exception_when_not_exists() {
        // given
        long commentId = 123L;

        // when
        Exception actualException = assertThrows(NotFoundException.class, () -> commentService.getComment(commentId));

        // then
        assertThat(actualException.getMessage()).isEqualTo(String.format(COMMENT_NOT_FOUND, 123L));
    }
}
