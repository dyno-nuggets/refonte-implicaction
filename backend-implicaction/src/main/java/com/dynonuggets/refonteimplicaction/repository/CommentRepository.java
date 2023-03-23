package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Comment;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countAllByPost_Id(long postId);

    Page<Comment> findByPostOrderById(Post post, Pageable pageable);

    List<Comment> findAllByUser(UserModel user);

}
