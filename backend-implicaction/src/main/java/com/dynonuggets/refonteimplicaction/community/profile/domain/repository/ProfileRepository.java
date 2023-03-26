package com.dynonuggets.refonteimplicaction.community.profile.domain.repository;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("squid:S00100")
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_UsernameAndUser_EnabledTrue(String username);

    Page<Profile> findAllByUser_UsernameNotAndUser_EnabledTrue(String id, Pageable pageable);
}
