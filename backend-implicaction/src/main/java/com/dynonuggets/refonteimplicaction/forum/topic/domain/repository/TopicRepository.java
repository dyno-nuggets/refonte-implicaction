package com.dynonuggets.refonteimplicaction.forum.topic.domain.repository;

import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicModel, Long> {
    Page<TopicModel> findByCategory(CategoryModel category, Pageable pageable);

    Optional<TopicModel> findFirstByCategoryOrderByLastActionDesc(CategoryModel category);

    boolean existsByCategory(CategoryModel category);

    Page<TopicModel> findAllByOrderByLastActionDesc(Pageable pageable);
}
