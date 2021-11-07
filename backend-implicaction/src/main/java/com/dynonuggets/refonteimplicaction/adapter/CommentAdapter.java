package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentAdapter {

    public Comment toModel(CommentDto dto, Post post, User user) {
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .createdAt(dto.getCreatedAt())
                .post(post)
                .user(user)
                .build();
    }

    public CommentDto toDto(Comment model) {
        return CommentDto.builder()
                .id(model.getId())
                .postId(model.getPost().getId())
                .createdAt(model.getCreatedAt())
                .text(model.getText())
                .username(model.getUser().getUsername())
                .build();
    }
}