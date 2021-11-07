package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoteAdapterTest {

    VoteAdapter voteAdapter = new VoteAdapter();

    @Test
    void toModel() {
        // given
        User user = User.builder().id(666L).username("lucifer").build();
        Post post = Post.builder().id(123L).build();
        Vote expectedVote = new Vote(null, VoteType.UPVOTE, post, user);
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 666L);

        // when
        final Vote actualVote = voteAdapter.toModel(voteDto, post, user);

        // then
        assertThat(actualVote).usingRecursiveComparison().isEqualTo(expectedVote);
    }
}
