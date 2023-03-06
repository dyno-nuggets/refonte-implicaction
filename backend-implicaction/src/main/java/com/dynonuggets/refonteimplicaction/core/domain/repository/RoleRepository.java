package com.dynonuggets.refonteimplicaction.core.domain.repository;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByNameIn(List<String> roles);
}
