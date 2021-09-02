package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("java:S100") // supprime la règle d'inspection des conventions de nommage
public interface SignUpRepository extends JpaRepository<Signup, Long> {
    Optional<Signup> findByActivationKey(String activationKey);

    Optional<Signup> findByUser_Username(String username);
}
