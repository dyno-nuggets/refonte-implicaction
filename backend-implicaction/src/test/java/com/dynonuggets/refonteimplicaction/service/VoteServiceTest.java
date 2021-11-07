package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.VoteAdapter;
import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import com.dynonuggets.refonteimplicaction.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.utils.Message.POST_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.USER_ALREADY_VOTE_FOR_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    AuthService authService;

    @Mock
    PostRepository postRepository;

    @Mock
    VoteAdapter voteAdapter;

    @InjectMocks
    VoteService voteService;

    @Captor
    ArgumentCaptor<Post> postArgumentCaptor;

    @Captor
    ArgumentCaptor<Vote> voteArgumentCaptor;

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

    @Test
    void should_upvote_when_user_has_no_vote_and_post_exists() {
        // given
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 123L);
        User user = User.builder().id(666L).username("lucifer").build();
        Post post = Post.builder().id(123L).voteCount(0).build();
        Vote vote = new Vote(23L, VoteType.UPVOTE, post, user);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.empty());
        given(voteAdapter.toModel(any(), any(), any())).willReturn(vote);

        // when
        voteService.vote(voteDto);

        // then
        verify(voteRepository, times(1)).save(any());
        verify(postRepository, times(1)).save(postArgumentCaptor.capture());
        final Post actualPost = postArgumentCaptor.getValue();

        assertThat(actualPost.getVoteCount()).isEqualTo(post.getVoteCount());
    }

    @Test
    void should_downvote_when_user_already_upvote() {
        // given
        VoteDto voteDto = new VoteDto(VoteType.DOWNVOTE, 123L);
        User user = User.builder().id(666L).username("lucifer").build();
        Post post = Post.builder().id(123L).voteCount(1).build();
        Vote oldVote = new Vote(23L, VoteType.UPVOTE, post, user);
        Vote newVote = new Vote(null, VoteType.DOWNVOTE, post, user);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.of(oldVote));
        given(voteAdapter.toModel(any(), any(), any())).willReturn(newVote);

        // when
        voteService.vote(voteDto);

        // then
        verify(voteRepository, times(1)).save(any());
        verify(postRepository, times(1)).save(postArgumentCaptor.capture());
        final Post actualPost = postArgumentCaptor.getValue();

        assertThat(actualPost.getVoteCount()).isZero();
    }

    @Test
    void should_throw_exception_when_voting_and_post_not_exists() {
        // given
        long postId = 123L;
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 3L);
        NotFoundException expectedException = new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId));
        given(postRepository.findById(anyLong())).willThrow(expectedException);

        // when
        final NotFoundException actualException = assertThrows(NotFoundException.class, () -> voteService.vote(voteDto));

        // then
        assertThat(actualException.getMessage()).isEqualTo(expectedException.getMessage());
    }

    @Test
    void should_throw_exception_when_already_vote_same_type() {
        // given
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 123L);
        User user = User.builder().id(666L).username("lucifer").build();
        Post post = Post.builder().id(123L).voteCount(0).build();
        Vote vote = new Vote(23L, VoteType.UPVOTE, post, user);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(voteRepository.findTopByPostAndUserOrderByIdDesc(any(), any())).willReturn(Optional.of(vote));

        // when
        final UnauthorizedException actualException = assertThrows(UnauthorizedException.class, () -> voteService.vote(voteDto));

        // then
        assertThat(actualException.getMessage()).isEqualTo(USER_ALREADY_VOTE_FOR_POST);
    }
}
