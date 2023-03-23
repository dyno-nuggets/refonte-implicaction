package com.dynonuggets.refonteimplicaction.user.domain.repository;

import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByUsernameAndEnabledTrue(String username);

    Optional<UserModel> findByActivationKey(String activationKey);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserModel> findAllByEnabledIsFalse(Pageable pageable);

}
