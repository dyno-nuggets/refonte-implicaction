package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import com.dynonuggets.refonteimplicaction.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    AuthService authService;

    @InjectMocks
    VoteService voteService;

    @Test
    void is_post_up_voted_should_return_false_when_not_logged_in() {
        // given
        final Post post = new Post();

        // when
        final boolean actual = voteService.isPostUpVoted(post);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void is_post_up_voted_should_return_true_when_logged_in() {
        // given
        final Post post = Post.builder().id(123L).build();
        final User user = User.builder().id(123L).build();
        final Vote vote = new Vote(123L, VoteType.UPVOTE, post, user);
        given(authService.isLoggedIn()).willReturn(true);
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.of(vote));

        // when
        final boolean actual = voteService.isPostUpVoted(post);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void is_post_up_voted_should_return_false_when_logged_in_and_not_voted() {
        // given
        final Post post = Post.builder().id(123L).build();
        final User user = User.builder().id(123L).build();
        given(authService.isLoggedIn()).willReturn(true);
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.empty());

        // when
        final boolean actual = voteService.isPostUpVoted(post);

        // then
        assertThat(actual).isFalse();
    }

    //////
    @Test
    void is_post_down_voted_should_return_false_when_not_logged_in() {
        // given
        final Post post = new Post();

        // when
        final boolean actual = voteService.isPostDownVoted(post);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void is_post_down_voted_should_return_true_when_logged_in() {
        // given
        final Post post = Post.builder().id(123L).build();
        final User user = User.builder().id(123L).build();
        final Vote vote = new Vote(123L, VoteType.DOWNVOTE, post, user);
        given(authService.isLoggedIn()).willReturn(true);
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.of(vote));

        // when
        final boolean actual = voteService.isPostDownVoted(post);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void is_post_down_voted_should_return_false_when_logged_in_and_not_voted() {
        // given
        final Post post = Post.builder().id(123L).build();
        given(authService.isLoggedIn()).willReturn(true);
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.empty());

        // when
        final boolean actual = voteService.isPostDownVoted(post);

        // then
        assertThat(actual).isFalse();
    }
}
