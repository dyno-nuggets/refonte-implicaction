package com.dynonuggets.refonteimplicaction.core.domain.repository;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(String role);
}
