package com.dynonuggets.refonteimplicaction.community.group.adapter;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Component
@AllArgsConstructor
public class GroupAdapter {

    protected static final String DEFAULT_GROUP_IMAGE_URI = "assets/img/avatar-ia-group.png";

    private FileService fileService;

    public Group toModel(final GroupDto dto, final Profile profile) {
        return Group.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .valid(dto.isValid())
                .profile(profile)
                .build();
    }

    public GroupDto toDto(final Group model) {
        final String imageUrl = ofNullable(fileService.buildFileUri(model.getImage())).orElse(DEFAULT_GROUP_IMAGE_URI);
        final String username = model.getProfile() != null ? model.getProfile().getUser().getUsername() : "";
        return GroupDto.builder()
                .id(model.getId())
                .name(model.getName())
                .numberOfPosts(emptyIfNull(model.getPosts()).size())
                .description(model.getDescription())
                .createdAt(model.getCreatedAt())
                .imageUrl(imageUrl)
                .valid(model.isValid())
                .username(username)
                .userId(callIfNotNull(model.getProfile(), Profile::getId))
                .numberOfUsers(emptyIfNull(model.getProfiles()).size())
                .build();
    }
}
