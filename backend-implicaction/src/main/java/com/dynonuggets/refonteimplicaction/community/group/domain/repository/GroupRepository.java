package com.dynonuggets.refonteimplicaction.community.group.domain.repository;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);

    Page<Group> findAllByEnabled(Pageable pageable, boolean enabled);

    Page<Group> findAllByEnabledIsTrue(Pageable pageable);
}
