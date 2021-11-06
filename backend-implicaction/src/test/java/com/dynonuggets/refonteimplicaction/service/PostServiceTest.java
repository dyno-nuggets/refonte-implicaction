package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.PostAdapter;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
import com.dynonuggets.refonteimplicaction.utils.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostAdapter postAdapter;

    @Mock
    PostRepository postRepository;

    @Mock
    SubredditRepository subredditRepository;

    @Mock
    AuthService authService;

    @Mock
    CommentService commentService;

    @Mock
    VoteService voteService;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @InjectMocks
    private PostService postService;

    @Test
    void should_save_post() {
        // given
        User currentUser = User.builder().id(123L).username("test user").build();
        Subreddit subreddit = new Subreddit(123L, "Super Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(null, "First Subreddit", "First Post", "http://url.site", "Test");

        given(subredditRepository.findByName(anyString())).willReturn(Optional.of(subreddit));
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(postAdapter.toPost(postRequest, subreddit, currentUser)).willReturn(expected);

        // when
        postService.save(postRequest);

        // then
        verify(postRepository, times(1)).save(postArgumentCaptor.capture());
        final Post actual = postArgumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void should_throw_exception_when_save_post_and_subreddit_not_found() {
        // given
        given(subredditRepository.findByName(anyString())).willReturn(Optional.empty());
        PostRequest postRequest = new PostRequest(null, "My Subreddit", "First Post", "http://url.site", "Test");

        // when
        Exception exception = assertThrows(NotFoundException.class, () -> postService.save(postRequest));

        String expectedMessage = String.format(Message.SUBREDDIT_NOT_FOUND_MESSAGE, "My Subreddit");
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(exception).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_post_when_exists() {
        // given
        User currentUser = User.builder().id(123L).username("Sankukai").build();
        Subreddit subreddit = new Subreddit(123L, "Super Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(12L, "Super Post", "http://url.site", "Test", 88000, currentUser, Instant.now(), subreddit);
        PostResponse expectedResponse = new PostResponse(123L, "Super post", "http://url.site", "Test", "Sankukai", "Super Subreddit", 88000, 12, null, true, false);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postAdapter.toPostResponse(any(Post.class), anyInt(), anyBoolean(), anyBoolean())).willReturn(expectedResponse);

        // when
        PostResponse actualResponse = postService.getPost(123L);

        // then
        assertThat(actualResponse.getId()).isEqualTo(expectedResponse.getId());
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
    }

    @Test
    void should_throw_exception_when_user_not_exists() {
        // given
        long postId = 123L;

        // when
        Exception exception = assertThrows(NotFoundException.class, () -> postService.getPost(postId));

        // then
        assertTrue(exception.getMessage().contains(String.format(Message.POST_NOT_FOUND_MESSAGE, postId)));
        assertThat(exception).isInstanceOf(NotFoundException.class);
    }
}
