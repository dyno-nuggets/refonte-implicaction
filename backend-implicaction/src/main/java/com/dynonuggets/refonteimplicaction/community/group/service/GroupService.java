package com.dynonuggets.refonteimplicaction.community.group.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.group.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupCreationRequest;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.error.GroupException;
import com.dynonuggets.refonteimplicaction.community.group.mapper.GroupMapper;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import com.dynonuggets.refonteimplicaction.filemanagement.service.FileService;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.GROUP_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.USER_ALREADY_SUBSCRIBED_TO_GROUP;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final CloudService cloudService;
    private final FileService fileService;
    private final ProfileService profileService;

    @Transactional
    public GroupDto createGroup(final GroupCreationRequest groupCreationRequest, final MultipartFile image) {
        final UserModel currentUser = authService.getCurrentUser();
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(currentUser.getUsername());

        final GroupModel.GroupModelBuilder builder = GroupModel.builder()
                .name(groupCreationRequest.getName())
                .description(groupCreationRequest.getDescription())
                .createdAt(now())
                .creator(profile)
                .enabled(currentUser.isAdmin())
                .createdAt(now());

        // TODO: permettre l'upload d'une image de groupe
        // if (image != null) {
        //            final FileModel fileModel = cloudService.uploadAvatar(image, username);
        //    builder.imageUrl(fileService.save(fileModel));
        //}

        return groupMapper.toDto(groupRepository.save(builder.build()));
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAllEnabledGroups(final Pageable pageable) {
        return groupRepository.findAllByEnabled(pageable, true)
                .map(groupMapper::toDto);
    }

    @Transactional
    public void subscribeGroup(final Long groupId) {
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(authService.getCurrentUser().getUsername());
        final GroupModel group = groupRepository.findByIdAndEnabledTrue(groupId)
                .orElseThrow(() -> new EntityNotFoundException(GROUP_NOT_FOUND, groupId.toString()));

        group.getProfiles().stream()
                .filter(p -> profile.getId().equals(p.getId()))
                .findAny()
                .ifPresentOrElse(
                        p -> {
                            throw new GroupException(USER_ALREADY_SUBSCRIBED_TO_GROUP, group.getName());
                        },
                        () -> {
                            group.getProfiles().add(profile);
                            groupRepository.save(group);
                        }
                );
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAllPendingGroups(final Pageable pageable) {
        return groupRepository.findAllByEnabled(pageable, false)
                .map(groupMapper::toDto);
    }

    @Transactional
    public GroupDto enableGroup(final Long groupId) {
        final GroupModel group = getByIdIfExists(groupId);

        group.setEnabled(true);
        final GroupModel groupUpdate = groupRepository.save(group);
        return groupMapper.toDto(groupUpdate);
    }

    @Transactional(readOnly = true)
    public GroupModel getByIdIfExists(final Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(GROUP_NOT_FOUND, groupId.toString()));
    }
}
