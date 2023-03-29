package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.core.utils.DateUtils;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;

@Component
@AllArgsConstructor
public class PostAdapter {

    public Post toPost(final PostRequest postRequest, final GroupModel group, final UserModel currentUser) {
        return Post.builder()
                .id(postRequest.getId())
                .name(postRequest.getName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0) // 0, car cette méthode n’est utilisée que lors de la création d’un post
                .user(currentUser)
                .createdAt(Instant.now())
                .group(group)
                .build();
    }

    public PostResponse toPostResponse(final Post post, final int commentCount, final boolean isPostUpVoted, final boolean isPostDownVoted) {
        final GroupModel group = post.getGroup();
        final String subredditImageUrl = callIfNotNull(group, GroupModel::getImageUrl);
        final String subredditName = callIfNotNull(group, GroupModel::getName);

        return PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .url(post.getUrl())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .userId(post.getUser().getId())
                .groupName(subredditName)
                .commentCount(commentCount)
                .duration(DateUtils.getDurationAsString(post.getCreatedAt()))
                .upVote(isPostUpVoted)
                .downVote(isPostDownVoted)
                .voteCount(post.getVoteCount())
                .subredditImageUrl(subredditImageUrl)
                .userImageUrl(null)
                .build();
    }

}
