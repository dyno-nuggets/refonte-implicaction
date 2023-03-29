package com.dynonuggets.refonteimplicaction.community.group.domain.repository;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupModel, Long> {
    Page<GroupModel> findAllByEnabled(Pageable pageable, boolean enabled);

    Optional<GroupModel> findByIdAndEnabledTrue(Long groupId);
}
