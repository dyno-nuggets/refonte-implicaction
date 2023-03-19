package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoteAdapterTest {

    VoteAdapter voteAdapter = new VoteAdapter();

    @Test
    void toModel() {
        // given
        final User user = User.builder().id(666L).username("lucifer").build();
        final Post post = Post.builder().id(123L).build();
        final Vote expectedVote = new Vote(null, VoteType.UPVOTE, post, user);
        final VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 666L);

        // when
        final Vote actualVote = voteAdapter.toModel(voteDto, post, user);

        // then
        assertThat(actualVote).usingRecursiveComparison().isEqualTo(expectedVote);
    }
}
