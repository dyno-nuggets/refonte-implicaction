package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.service.FileService;
import com.dynonuggets.refonteimplicaction.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PostAdapter {

    private FileService fileService;

    public Post toPost(PostRequest postRequest, Group group, User currentUser) {
        return Post.builder()
                .id(postRequest.getId())
                .name(postRequest.getName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0) // 0 car cette méthode n'est utilisée que lors de la création d'un post
                .user(currentUser)
                .createdAt(Instant.now())
                .group(group)
                .build();
    }

    public PostResponse toPostResponse(Post post, int commentCount, boolean isPostUpVoted, boolean isPostDownVoted) {
        final Group group = post.getGroup();
        final String subredditImageUrl = group != null && group.getImage() != null ? group.getImage().getUrl() : null;
        final String subredditName = group != null ? group.getName() : "";
        final String userImageKey = post.getUser().getImage() != null ? fileService.buildFileUri(post.getUser().getImage().getObjectKey()) : null;

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
                .userImageUrl(userImageKey)
                .build();
    }

}
