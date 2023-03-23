package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class VoteAdapter {

    public Vote toModel(final VoteDto voteDto, final Post post, final UserModel user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
