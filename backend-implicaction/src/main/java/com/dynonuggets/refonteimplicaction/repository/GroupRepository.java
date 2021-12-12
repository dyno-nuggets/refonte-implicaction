package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select s from Group s order by s.posts.size desc")
    List<Group> findAllByTopPosting(Pageable pageable);

    Optional<Group> findByName(String name);

    Page<Group> findAllByValidIsFalse(Pageable pageable);

    Page<Group> findAllByValidIsTrue(Pageable pageable);
}
