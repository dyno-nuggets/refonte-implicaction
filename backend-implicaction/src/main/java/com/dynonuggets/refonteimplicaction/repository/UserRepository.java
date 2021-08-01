package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
}
