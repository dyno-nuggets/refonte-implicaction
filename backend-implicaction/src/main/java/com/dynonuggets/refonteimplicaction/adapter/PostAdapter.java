package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PostAdapter {

    public Post toPost(PostRequest postRequest, Subreddit subreddit, User currentUser) {
        return Post.builder()
                .id(postRequest.getId())
                .name(postRequest.getName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0)
                .user(currentUser)
                .createdDate(Instant.now())
                .subreddit(subreddit)
                .build();
    }

    public PostResponse toPostResponse(Post post, int commentCount, boolean isPostUpVoted, boolean isPostDownVoted) {
        return PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .url(post.getUrl())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .commentCount(commentCount)
                .duration(DateUtils.getDurationAsString(post.getCreatedDate()))
                .upVote(isPostUpVoted)
                .downVote(isPostDownVoted)
                .build();
    }

}
