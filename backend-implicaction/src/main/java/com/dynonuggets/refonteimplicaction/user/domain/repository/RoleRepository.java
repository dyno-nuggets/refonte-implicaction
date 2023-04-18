package com.dynonuggets.refonteimplicaction.user.domain.repository;

import com.dynonuggets.refonteimplicaction.user.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(RoleEnum roleEnum);
}
