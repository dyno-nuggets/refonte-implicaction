package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
@AllArgsConstructor
public class GroupAdapter {

    protected static final String DEFAULT_GROUP_IMAGE_URI = "assets/img/avatar-ia-group.png";

    private FileService fileService;

    public Group toModel(GroupDto dto, User user) {
        return Group.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .valid(dto.isValid())
                .user(user)
                .build();
    }

    public GroupDto toDto(Group model) {
        final String imageUrl = model.getImage() != null ? fileService.buildFileUri(model.getImage().getObjectKey()) : DEFAULT_GROUP_IMAGE_URI;
        final String username = model.getUser() != null ? model.getUser().getUsername() : "";
        final Long userId = model.getUser() != null ? model.getUser().getId() : null;
        return GroupDto.builder()
                .id(model.getId())
                .name(model.getName())
                .numberOfPosts(isNotEmpty(model.getPosts()) ? model.getPosts().size() : 0)
                .description(model.getDescription())
                .createdAt(model.getCreatedAt())
                .imageUrl(imageUrl)
                .valid(model.isValid())
                .username(username)
                .userId(userId)
                .numberOfUsers(isNotEmpty(model.getUsers()) ? model.getUsers().size() : 0)
                .build();
    }
}
