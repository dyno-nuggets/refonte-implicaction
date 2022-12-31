package com.dynonuggets.refonteimplicaction.repository.forum;

import com.dynonuggets.refonteimplicaction.model.forum.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIdIsNull();
}
