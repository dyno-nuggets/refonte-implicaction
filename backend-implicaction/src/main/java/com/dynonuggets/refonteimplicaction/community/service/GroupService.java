package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.domain.repository.GroupRepository;
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

import static com.dynonuggets.refonteimplicaction.core.util.Message.GROUP_NOT_FOUND_MESSAGE;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupAdapter groupAdapter;
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final CloudService cloudService;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupDto save(final MultipartFile image, final GroupDto groupDto) {
        final FileModel fileModel = cloudService.uploadImage(image);
        final FileModel fileSave = fileRepository.save(fileModel);
        final User user = authService.getCurrentUser();
        final Group group = groupAdapter.toModel(groupDto, user);
        group.setImage(fileSave);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());

        final Group save = groupRepository.save(group);

        return groupAdapter.toDto(save);
    }

    @Transactional
    public GroupDto save(final GroupDto groupDto) {
        final User user = authService.getCurrentUser();
        final Group group = groupAdapter.toModel(groupDto, user);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());
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

    @Transactional
    public List<GroupDto> addGroup(final String groupName) {
        final User user = authService.getCurrentUser();
        final Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format(GROUP_NOT_FOUND_MESSAGE, groupName)));

        user.getGroups().add(group);
        userRepository.save(user);
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
