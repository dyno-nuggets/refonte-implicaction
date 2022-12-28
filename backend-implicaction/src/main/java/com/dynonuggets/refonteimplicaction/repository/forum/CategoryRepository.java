package com.dynonuggets.refonteimplicaction.repository.forum;

import com.dynonuggets.refonteimplicaction.model.forum.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
