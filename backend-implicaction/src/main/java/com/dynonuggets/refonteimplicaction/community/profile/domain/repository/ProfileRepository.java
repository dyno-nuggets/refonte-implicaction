package com.dynonuggets.refonteimplicaction.community.profile.domain.repository;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("squid:S00100")
public interface ProfileRepository extends JpaRepository<ProfileModel, Long>, ProfileRepositoryCustom {
    Optional<ProfileModel> findByUser_UsernameAndUser_EnabledTrue(String username);

    Optional<ProfileModel> findByUser_Username(String username);
}
