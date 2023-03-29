package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostAdapterTest {
    @InjectMocks
    PostAdapter postAdapter;

    @Test
    void toPost() {
        // given
        final UserModel currentUser = UserModel.builder().id(123L).username("test user").build();
        final GroupModel group = GroupModel.builder().build();
        final Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, currentUser, now(), group);
        final PostRequest postRequest = new PostRequest(123L, null, "Super Post", "http://url.site", "Test");

        // when
        final Post actual = postAdapter.toPost(postRequest, group, currentUser);

        // then
        assertThat(actual).usingRecursiveComparison()
                // la date de création n’est pas testable car celle de expected est créée avant celle de actual
                .ignoringFieldsOfTypes(Instant.class)
                .isEqualTo(expected);
    }

    @Test
    void should_return_post_with_no_subreddit_and_poster_has_no_image() {
        final UserModel currentUser = UserModel.builder().id(123L).username("test user").build();
        final Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), null);
        final int expectedCommentCount = 10;

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, expectedCommentCount, true, false);

        // then
        assertThat(postResponse.getGroupName()).isNullOrEmpty();
    }

    @Test
    void should_return_post_with_subreddit_image_null() {
        final UserModel currentUser = UserModel.builder().id(123L).username("test user").build();
        final GroupModel group = GroupModel.builder().build();
        final Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), group);
        final int expectedCommentCount = 10;

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, expectedCommentCount, true, false);

        // then
        assertThat(postResponse.getSubredditImageUrl()).isNull();
    }

    @Test
    void toPostResponse() {
        // given
        final UserModel currentUser = UserModel.builder().id(123L).username("test user").build();
        final GroupModel group = GroupModel.builder().build();
        final Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 12, currentUser, now(), group);
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
        assertThat(postResponse.isDownVote()).isFalse();
        assertThat(postResponse.isUpVote()).isTrue();
    }
}
