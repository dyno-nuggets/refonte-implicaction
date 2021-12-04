package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class CommentAdapter {

    public Comment toModel(CommentDto dto, Post post, User user) {
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .post(post)
                .user(user)
                .build();
    }

    public CommentDto toDto(Comment model) {
        final FileModel userImage = model.getUser().getImage();
        final String userImageUrl = userImage != null ? userImage.getUrl() : null;
        return CommentDto.builder()
                .id(model.getId())
                .postId(model.getPost().getId())
                .duration(DateUtils.getDurationAsString(model.getCreatedAt()))
                .text(model.getText())
                .username(model.getUser().getUsername())
                .userId(model.getUser().getId())
                .userImageUrl(userImageUrl)
                .build();
    }
}
