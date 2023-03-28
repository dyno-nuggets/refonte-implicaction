package com.dynonuggets.refonteimplicaction.community.group.mapper;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupConstants.DEFAULT_GROUP_IMAGE_URI;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.defaultIfNull;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Mapper
public interface GroupMapper {

    @Mapping(target = "numberOfUsers", expression = "java(countProfiles(model.getProfiles()))")
    @Mapping(target = "creator", expression = "java(getCreatorUsername(model.getCreator()))")
    @Mapping(target = "imageUrl", expression = "java(getImageUrl(model.getImageUrl()))")
    GroupDto toDto(GroupModel model);

    default int countProfiles(final List<ProfileModel> profiles) {
        return emptyIfNull(profiles).size();
    }

    default String getCreatorUsername(final ProfileModel profile) {
        if (profile == null) {
            return null;
        }
        final UserModel user = profile.getUser();
        return callIfNotNull(user, UserModel::getUsername);
    }

    default String getImageUrl(final String imageUrl) {
        return defaultIfNull(imageUrl, DEFAULT_GROUP_IMAGE_URI);
    }
}
