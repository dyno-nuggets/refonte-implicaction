package com.dynonuggets.refonteimplicaction.forum.category.domain.repository;

import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    List<CategoryModel> findByParentIdIsNull();
}
