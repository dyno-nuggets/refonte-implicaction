package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByGroupId(Pageable pageable, Long groupId);

    Page<Post> findByGroupIdAndNameContainingIgnoreCase(Pageable pageable, Long groupId, String query);

    Page<Post> findByNameContainingIgnoreCase(Pageable pageable, String query);

    Page<Post> findByGroupIdOrderByViewsDesc(Pageable pageable, Long groupId);

}
