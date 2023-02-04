package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.core.util.DateUtils;
import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Post;
import org.springframework.stereotype.Component;

@Component
public class CommentAdapter {

    public Comment toModel(final CommentDto dto, final Post post, final User user) {
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .post(post)
                .user(user)
                .build();
    }

    public CommentDto toDto(final Comment model) {
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
