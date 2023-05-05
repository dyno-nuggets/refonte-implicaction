package com.dynonuggets.refonteimplicaction.community.profile.domain.repository;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileRepositoryCustom {

    Page<ProfileModel> findAllProfilesWithRelationTypeCriteria(final String username, final RelationCriteriaEnum relationCriteria, final Pageable pageable);

    Long countFriendRequestsByUsername(String username);
}
