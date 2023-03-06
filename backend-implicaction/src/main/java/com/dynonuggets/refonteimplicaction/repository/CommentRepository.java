package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.core.domain.model.User;
import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    Page<Comment> findByPostOrderById(Post post, Pageable pageable);

    List<Comment> findAllByUser(User user);

}
