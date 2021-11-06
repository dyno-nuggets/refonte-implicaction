package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class PostAdapterTest {

    PostAdapter postAdapter = new PostAdapter();

    @Test
    void toPost() {
        // given
        User currentUser = User.builder().id(123L).username("test user").build();
        Subreddit subreddit = new Subreddit(123L, "Super Subreddit", "Subreddit Description", emptyList(), now(), currentUser);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, currentUser, now(), subreddit);
        PostRequest postRequest = new PostRequest(123L, "First Subreddit", "Super Post", "http://url.site", "Test");

        // when
        final Post actual = postAdapter.toPost(postRequest, subreddit, currentUser);

        // then
        assertThat(actual).usingRecursiveComparison()
                // la date de création n'est pas testable car celle de expected est créée avant celle de actual
                .ignoringFieldsOfTypes(Instant.class)
                .isEqualTo(expected);
    }

    @Test
    void toPostResponse() {
        // given
        User currentUser = User.builder().id(123L).username("test user").build();
        Subreddit subreddit = new Subreddit(123L, "Super Subreddit", "Subreddit Description", emptyList(), now(), currentUser);
        Post expected = new Post(123L, "Super Post", "http://url.site", "Test", 0, currentUser, now(), subreddit);

        // when
        final PostResponse postResponse = postAdapter.toPostResponse(expected, 10, true, false);

        // then
        assertThat(postResponse.getId()).isEqualTo(expected.getId());
        assertThat(postResponse.getName()).isEqualTo(expected.getName());
        assertThat(postResponse.getUrl()).isEqualTo(expected.getUrl());
        assertThat(postResponse.getDescription()).isEqualTo(expected.getDescription());
        assertThat(postResponse.getUsername()).isEqualTo(expected.getUser().getUsername());
        assertThat(postResponse.getSubredditName()).isEqualTo(expected.getSubreddit().getName());
        assertThat(postResponse.getCommentCount()).isEqualTo(10);
        assertThat(postResponse.isDownVote()).isFalse();
        assertThat(postResponse.isUpVote()).isTrue();
    }
}