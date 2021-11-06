package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public int commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
}
