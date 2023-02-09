package com.dynonuggets.refonteimplicaction.auth.domain.repository;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByActivationKey(String activationKey);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // TODO: à modifier par EnableIsFalse
    Page<User> findAllByActiveIsFalse(Pageable pageable);

    List<User> findAllByRoles_NameIn(List<String> roleName);

    Page<User> findAllByIdNot(Pageable pageable, Long currentUserId);
}
