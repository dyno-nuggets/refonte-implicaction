package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
class SubredditRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    SubredditRepository subredditRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void findAllByTopPosting() {
        // given
        int limit = 5;
        final Subreddit s1 = Subreddit.builder().id(1L).name("sub1").description("sub1").createdAt(Instant.now()).build();
        final Subreddit s2 = Subreddit.builder().id(2L).name("sub2").description("sub2").createdAt(Instant.now()).build();
        final Subreddit s3 = Subreddit.builder().id(3L).name("sub3").description("sub3").createdAt(Instant.now()).build();
        final Subreddit s4 = Subreddit.builder().id(4L).name("sub4").description("sub4").createdAt(Instant.now()).build();
        final Subreddit s5 = Subreddit.builder().id(5L).name("sub5").description("sub5").createdAt(Instant.now()).build();
        final Subreddit s6 = Subreddit.builder().id(6L).name("sub6").description("sub6").createdAt(Instant.now()).build();
        final Subreddit s7 = Subreddit.builder().id(7L).name("sub7").description("sub7").createdAt(Instant.now()).build();
        final Subreddit s8 = Subreddit.builder().id(8L).name("sub8").description("sub8").createdAt(Instant.now()).build();
        final Subreddit s9 = Subreddit.builder().id(9L).name("sub9").description("sub9").createdAt(Instant.now()).build();
        List<Subreddit> subreddits = asList(s1, s2, s3, s4, s5, s6, s7, s8, s9);

        subredditRepository.saveAll(subreddits);

        List<Post> s1Posts = Stream.of(1, 2, 3, 4, 5)
                .map(id -> Post.builder().name("bla").subreddit(s1).build())
                .collect(toList());

        List<Post> s2Posts = Stream.of(1, 2, 3, 4, 5, 6, 7)
                .map(id -> Post.builder().name("bla").subreddit(s2).build())
                .collect(toList());

        List<Post> s3Posts = Stream.of(1, 2)
                .map(id -> Post.builder().name("bla").subreddit(s3).build())
                .collect(toList());

        List<Post> s5Posts = Stream.of(1, 2, 3)
                .map(id -> Post.builder().name("bla").subreddit(s5).build())
                .collect(toList());

        List<Post> s6Posts = Stream.of(1, 2, 3, 4, 5)
                .map(id -> Post.builder().name("bla").subreddit(s6).build())
                .collect(toList());

        List<Post> s8Posts = Stream.of(1, 2, 3, 4, 5, 8, 9)
                .map(id -> Post.builder().name("bla").subreddit(s8).build())
                .collect(toList());

        final List<Post> allPosts = Stream.of(s1Posts, s2Posts, s3Posts, s5Posts, s6Posts, s8Posts).flatMap(Collection::stream).collect(toList());

        postRepository.saveAll(allPosts);

        final List<String> expected = allPosts.stream()
                .collect(groupingBy(Post::getSubreddit))
                .entrySet()
                .stream()
                .collect(toMap(o -> o.getKey().getName(), subredditListEntry -> subredditListEntry.getValue().size()))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .map(Map.Entry::getKey)
                .limit(limit)
                .collect(toList());

        // when
        final List<Subreddit> actual = subredditRepository.findAllByTopPosting(Pageable.ofSize(limit));

        // then
        assertThat(actual.size()).isEqualTo(limit);
        assertThat(actual.stream().map(Subreddit::getName).collect(toList())).containsAll(expected);
    }
}
