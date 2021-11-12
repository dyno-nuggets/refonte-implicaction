package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SubredditAdapterTest {

    SubredditAdapter subredditAdapter = new SubredditAdapter();

    @Test
    void toModel() {
        // given
        SubredditDto expected = SubredditDto.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .build();

        // when
        final Subreddit actual = subredditAdapter.toModel(expected);

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts", "createdAt", "image")
                .isEqualTo(expected);
    }

    @Test
    void toDto() {
        // given
        Subreddit expectedModel = Subreddit.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .posts(Arrays.asList(new Post(), new Post(), new Post(), new Post()))
                .createdAt(Instant.now())
                .build();

        // when
        final SubredditDto actualDto = subredditAdapter.toDto(expectedModel);

        // then
        assertThat(actualDto).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts", "imageUrl")
                .isEqualTo(expectedModel);

        assertThat(actualDto.getNumberOfPosts()).isEqualTo(expectedModel.getPosts().size());
    }
}
