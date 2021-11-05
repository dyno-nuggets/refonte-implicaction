package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SubredditAdapterTest {

    SubredditAdapter subredditAdapter = new SubredditAdapter();

    @Test
    void toModel() {
        SubredditDto expected = SubredditDto.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .build();

        assertThat(subredditAdapter.toModel(expected)).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts", "createdAt")
                .isEqualTo(expected);
    }

    @Test
    void toDto() {
        Subreddit expected = Subreddit.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .createdAt(Instant.now())
                .build();

        assertThat(subredditAdapter.toDto(expected)).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts")
                .isEqualTo(expected);
    }
}
