package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.PostAdapter;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.utils.Message.*;
import static java.util.Arrays.asList;
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
    GroupRepository groupRepository;

    @Mock
    AuthService authService;

    @Mock
    CommentService commentService;

    @Mock
    VoteService voteService;

    @Captor
    ArgumentCaptor<Post> argumentCaptor;

    @InjectMocks
    PostService postService;

    @Test
    void should_save_post_if_subreddit_exists() {
        // given
        User currentUser = User.builder().id(123L).username("test user").build();
        Group group = new Group(123L, "Super Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser, null, emptyList(), true);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(123L, 123L, "First Subreddit", "http://url.site", "Test");

        given(groupRepository.findById(anyLong())).willReturn(Optional.of(group));
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(postAdapter.toPost(postRequest, group, currentUser)).willReturn(expected);

        // when
        postService.saveOrUpdate(postRequest);

        // then
        verify(postRepository, times(1)).save(argumentCaptor.capture());
        final Post actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void should_throw_exception_on_save_when_subreddit_not_exists() {
        // given
        long subredditId = 1234L;
        NotFoundException expectedException = new NotFoundException(String.format(SUBREDDIT_NOT_FOUND_MESSAGE, subredditId));
        given(groupRepository.findById(anyLong())).willThrow(expectedException);
        PostRequest postRequest = PostRequest.builder().name("test").groupId(subredditId).build();

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> postService.saveOrUpdate(postRequest));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_throw_exception_on_save_when_name_is_empty() {
        // given
        PostRequest postRequest = PostRequest.builder().build();

        // when
        final IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> postService.saveOrUpdate(postRequest));

        // then
        assertThat(actualException.getMessage()).isEqualTo(POST_SHOULD_HAVE_A_NAME);
    }

    @Test
    void should_throw_exception_when_save_post_and_subreddit_not_found() {
        // given
        PostRequest postRequest = new PostRequest(123L, 123L, "First Post", "http://url.site", "Test");

        given(groupRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Exception actualException = assertThrows(NotFoundException.class, () -> postService.saveOrUpdate(postRequest));

        String expectedMessage = String.format(SUBREDDIT_NOT_FOUND_MESSAGE, postRequest.getGroupId());

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void should_get_post_when_exists() {
        // given
        User currentUser = User.builder().id(123L).username("Sankukai").build();
        Group group = new Group(123L, "Super Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser, null, emptyList(), true);
        Post post = new Post(12L, "Super Post", "http://url.site", "Test", 88000, currentUser, Instant.now(), group);
        PostResponse expectedResponse = new PostResponse(123L, "Super post", "http://url.site", "Test", "Sankukai", currentUser.getId(), null, "Super Subreddit", 88000, 12, null, true, false, null);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postAdapter.toPostResponse(any(Post.class), anyInt(), anyBoolean(), anyBoolean())).willReturn(expectedResponse);

        // when
        PostResponse actualResponse = postService.getPost(123L);

        // then
        assertThat(actualResponse.getId()).isEqualTo(expectedResponse.getId());
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
    }

    @Test
    void should_throw_exception_when_post_not_exists() {
        // given
        long postId = 123L;

        // when
        Exception exception = assertThrows(NotFoundException.class, () -> postService.getPost(postId));

        // then
        assertTrue(exception.getMessage().contains(String.format(POST_NOT_FOUND_MESSAGE, postId)));
    }

    @Test
    void should_list_all_posts() {
        // given
        User currentUser = User.builder().id(1345L).username("gustave").build();
        Group group = new Group(123L, "Sub 1", "Description Sub 1", null, Instant.now(), currentUser, null, emptyList(), true);
        Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
        Page<Post> expectedPages = new PageImpl<>(asList(
                new Post(1L, "Post 1", null, "Description 1", 0, currentUser, Instant.now(), group),
                new Post(2L, "Post 2", null, "Description 2", 0, currentUser, Instant.now(), group),
                new Post(3L, "Post 3", null, "Description 3", 0, currentUser, Instant.now(), group)
        ));

        given(postRepository.findAll(any(Pageable.class))).willReturn(expectedPages);

        // when
        Page<PostResponse> actualPages = postService.getAllPosts(pageable);

        // then
        assertThat(actualPages.getSize()).isEqualTo(expectedPages.getSize());
    }

}
