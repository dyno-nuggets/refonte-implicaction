package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByActivationKey(String activationKey);

    List<User> findAllByUsernameOrEmail(String username, String email);

    Page<User> findAllByActivatedAtIsNull(Pageable pageable);

    List<User> findAllByRoles_NameIn(List<String> roleName);

    @Query("select u from User u " +
            "where u.id <> :currentUserId " +
            "and exists (select 1 from JobSeeker s where u.id = s.user.id) " +
            "or exists (select 1 from Recruiter r where u.id = r.user.id) ")
    Page<User> findAllForCommunity(Pageable pageable, @Param("currentUserId") Long currentUserId);
}
