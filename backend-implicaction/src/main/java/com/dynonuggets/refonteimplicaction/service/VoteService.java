package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.VoteAdapter;
import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Vote;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import com.dynonuggets.refonteimplicaction.repository.PostRepository;
import com.dynonuggets.refonteimplicaction.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.model.VoteType.DOWNVOTE;
import static com.dynonuggets.refonteimplicaction.model.VoteType.UPVOTE;
import static com.dynonuggets.refonteimplicaction.utils.Message.POST_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.USER_ALREADY_VOTE_FOR_POST;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final VoteAdapter voteAdapter;

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

    @Transactional
    public void vote(VoteDto voteDto) {
        final Long postId = voteDto.getPostId();
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        final Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

        voteByPostAndUser.ifPresent(vote -> {
                    // on ne peut pas voter 2 fois de la même manière
                    if (vote.getVoteType().equals(voteDto.getVoteType())) {
                        throw new UnauthorizedException(USER_ALREADY_VOTE_FOR_POST);
                    }
                }
        );

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        final Vote vote = voteAdapter.toModel(voteDto, post, authService.getCurrentUser());

        voteRepository.save(vote);
        postRepository.save(post);
    }
}
