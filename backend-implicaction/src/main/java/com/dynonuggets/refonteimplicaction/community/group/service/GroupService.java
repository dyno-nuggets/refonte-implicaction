package com.dynonuggets.refonteimplicaction.community.group.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.group.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.community.group.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.group.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.CloudService;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.Message.GROUP_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupAdapter groupAdapter;
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final CloudService cloudService;
    private final FileRepository fileRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @Transactional
    public GroupDto save(final MultipartFile image, final GroupDto groupDto) {
        final FileModel fileModel = cloudService.uploadImage(image);
        final FileModel fileSave = fileRepository.save(fileModel);
        final String username = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(username);

        final Group group = groupAdapter.toModel(groupDto, profile);
        group.setImage(fileSave);
        group.setCreatedAt(Instant.now());
        group.setCreator(profile);

        final Group save = groupRepository.save(group);

        return groupAdapter.toDto(save);
    }

    @Transactional
    public GroupDto save(final GroupDto groupDto) {
        final String username = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(username);
        final Group group = groupAdapter.toModel(groupDto, profile);
        group.setCreatedAt(Instant.now());
        group.setCreator(profile);
        final Group save = groupRepository.save(group);
        return groupAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAllValidGroups(final Pageable pageable) {
        final Page<Group> subreddits = groupRepository.findAllByEnabled(pageable, true);
        return subreddits.map(groupAdapter::toDto);
    }

    // TODO: déplacer dans le ProfileController : /profiles/{username}/groups/{groupId}/subscribe + faire unsubscribe
    @Transactional
    public List<GroupDto> addGroup(final String groupName) {
        final ProfileModel user = profileService.getByUsernameIfExistsAndUserEnabled(authService.getCurrentUser().getUsername());
        final Group group = groupRepository.findByName(groupName)
                // TODO: lancer une exception appropriée
                .orElseThrow(() -> new NotFoundException(String.format(GROUP_NOT_FOUND_MESSAGE, groupName)));

        user.getGroups().add(group);
        profileRepository.save(user);
        return user.getGroups().stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }

    @Transactional
    public Page<GroupDto> getAllPendingGroups(final Pageable pageable) {
        return groupRepository.findAllByEnabled(pageable, false)
                .map(groupAdapter::toDto);
    }

    @Transactional
    public GroupDto enableGroup(final String groupName) {
        final Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format(GROUP_NOT_FOUND_MESSAGE, groupName)));

        group.setEnabled(true);
        final Group groupUpdate = groupRepository.save(group);
        return groupAdapter.toDto(groupUpdate);
    }
}
