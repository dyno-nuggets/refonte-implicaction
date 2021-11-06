package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import com.dynonuggets.refonteimplicaction.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.model.VoteType.DOWNVOTE;
import static com.dynonuggets.refonteimplicaction.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final AuthService authService;

    public boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    public boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (!authService.isLoggedIn()) {
            return false;
        }
        Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByIdDesc(
                post,
                authService.getCurrentUser()
        );
        return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
    }

}
