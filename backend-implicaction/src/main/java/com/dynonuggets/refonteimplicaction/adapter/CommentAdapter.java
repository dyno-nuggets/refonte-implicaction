package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class CommentAdapter {

    public Comment toModel(CommentDto dto, Post post, User user, Group group) {
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .post(post)
                .user(user)
                .group(group)
                .build();
    }

    public CommentDto toDto(Comment model) {
        final FileModel userImage = model.getUser().getImage();
        final String userImageUrl = userImage != null ? userImage.getUrl() : null;
        return CommentDto.builder()
                .id(model.getId())
                .postId(model.getPost().getId())
                .groupId(model.getGroup().getId())
                .duration(DateUtils.getDurationAsString(model.getCreatedAt()))
                .text(model.getText())
                .username(model.getUser().getUsername())
                .userId(model.getUser().getId())
                .userImageUrl(userImageUrl)
                .build();
    }
}
