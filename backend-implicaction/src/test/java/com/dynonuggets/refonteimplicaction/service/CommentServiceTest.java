package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void should_return_post_count() {
        Post post = new Post();
        final List<Comment> expectedComments = Arrays.asList(new Comment(), new Comment(), new Comment(), new Comment());
        // given
        given(commentRepository.findByPost(any())).willReturn(expectedComments);

        // when
        final int actualCount = commentService.commentCount(post);

        // then
        assertThat(actualCount).isEqualTo(expectedComments.size());
    }
}
