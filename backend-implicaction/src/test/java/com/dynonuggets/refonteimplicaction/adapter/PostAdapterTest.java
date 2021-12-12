package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostAdapterTest {

    @Mock
    FileService fileService;

    @InjectMocks
    PostAdapter postAdapter;

    @Test
    void toPost() {
        // given
        User currentUser = User.builder().id(123L).username("test user").build();
        Group group = new Group(123L, "Super Subreddit", "Subreddit Description", emptyList(), now(), currentUser, null, emptyList(), true);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, currentUser, now(), group);
        PostRequest postRequest = new PostRequest(123L, null, "Super Post", "http://url.site", "Test");

        // when
        final Post actual = postAdapter.toPost(postRequest, group, currentUser);

        // then
        assertThat(actual).usingRecursiveComparison()
                // la date de création n'est pas testable car celle de expected est créée avant celle de actual
                .ignoringFieldsOfTypes(Instant.class)
                .isEqualTo(expected);
    }

    @Test
    void should_return_post_with_no_subreddit_and_poster_has_no_image() {
        User currentUser = User.builder().id(123L).username("test user").build();
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), null);
        final int expectedCommentCount = 10;

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, expectedCommentCount, true, false);

        // then
        assertThat(postResponse.getGroupName()).isEmpty();
    }

    @Test
    void should_return_post_with_subreddit_image_null() {
        User currentUser = User.builder().id(123L).username("test user").build();
        Group group = new Group(123L, "Super Subreddit", "Subreddit Description", emptyList(), now(), currentUser, null, emptyList(), true);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), group);
        final int expectedCommentCount = 10;

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, expectedCommentCount, true, false);

        // then
        assertThat(postResponse.getSubredditImageUrl()).isNull();
    }

    @Test
    void toPostResponse() {
        // given
        User currentUser = User.builder().id(123L).username("test user").image(FileModel.builder().url("http://url.com").build()).build();
        Group group = new Group(123L, "Super Subreddit", "Subreddit Description", emptyList(), now(), currentUser, FileModel.builder().url("http://img.com").build(), emptyList(), true);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), group);
        final int expectedCommentCount = 10;

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, expectedCommentCount, true, false);

        // then
        assertThat(postResponse.getId()).isEqualTo(expected.getId());
        assertThat(postResponse.getName()).isEqualTo(expected.getName());
        assertThat(postResponse.getUrl()).isEqualTo(expected.getUrl());
        assertThat(postResponse.getDescription()).isEqualTo(expected.getDescription());
        assertThat(postResponse.getUsername()).isEqualTo(expected.getUser().getUsername());
        assertThat(postResponse.getGroupName()).isEqualTo(expected.getGroup().getName());
        assertThat(postResponse.getCommentCount()).isEqualTo(expectedCommentCount);
        assertThat(postResponse.getVoteCount()).isEqualTo(expected.getVoteCount());
        assertThat(postResponse.getSubredditImageUrl()).isEqualTo(expected.getGroup().getImage().getUrl());
        assertThat(postResponse.isDownVote()).isFalse();
        assertThat(postResponse.isUpVote()).isTrue();
    }
}
