package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.community.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.error.CommunityException;
import com.dynonuggets.refonteimplicaction.community.rest.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.CloudService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.error.CommunityErrorResult.PROFILE_NOT_FOUND;
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

    @Transactional
    public GroupDto save(final MultipartFile image, final GroupDto groupDto) {
        final FileModel fileModel = cloudService.uploadImage(image);
        final FileModel fileSave = fileRepository.save(fileModel);
        final String username = callIfNotNull(authService.getCurrentUser(), User::getUsername);
        final Profile profile = profileRepository.findByUser_Username(username)
                .orElseThrow(() -> new CommunityException(PROFILE_NOT_FOUND, username));

        final Group group = groupAdapter.toModel(groupDto, profile);
        group.setImage(fileSave);
        group.setCreatedAt(Instant.now());
        group.setProfile(profile);

        final Group save = groupRepository.save(group);

        return groupAdapter.toDto(save);
    }

    @Transactional
    public GroupDto save(final GroupDto groupDto) {
        final String username = callIfNotNull(authService.getCurrentUser(), User::getUsername);
        final Profile profile = profileRepository.findByUser_Username(username)
                .orElseThrow(() -> new CommunityException(PROFILE_NOT_FOUND, username));
        final Group group = groupAdapter.toModel(groupDto, profile);
        group.setCreatedAt(Instant.now());
        group.setProfile(profile);
        final Group save = groupRepository.save(group);
        return groupAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAllValidGroups(final Pageable pageable) {
        final Page<Group> subreddits = groupRepository.findAllByValidIsTrue(pageable);
        return subreddits.map(groupAdapter::toDto);
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllByTopPosting(final int limit) {
        final List<Group> topPostings = groupRepository.findAllByTopPosting(Pageable.ofSize(limit));
        return topPostings.stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }

    // TODO: déplacer dans le ProfileController : /profiles/{username}/groups/{groupId}/subscribe + faire unsubscribe
    @Transactional
    public List<GroupDto> addGroup(final String groupName) {
        final Profile user = profileRepository.findById(authService.getCurrentUser().getId())
                // TODO: lancer une exception appropriée
                .orElseThrow(() -> new NotFoundException(""));
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
        return groupRepository.findAllByValidIsFalse(pageable)
                .map(groupAdapter::toDto);
    }

    @Transactional
    public GroupDto validateGroup(final String groupName) {
        final Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format(GROUP_NOT_FOUND_MESSAGE, groupName)));

        group.setValid(true);
        final Group groupUpdate = groupRepository.save(group);
        return groupAdapter.toDto(groupUpdate);
    }
}
