package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

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
    public GroupDto save(MultipartFile image, GroupDto groupDto) {
        final FileModel fileModel = cloudService.uploadImage(image);
        final FileModel fileSave = fileRepository.save(fileModel);

        Group group = groupAdapter.toModel(groupDto);
        group.setImage(fileSave);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());

        final Group save = groupRepository.save(group);

        return groupAdapter.toDto(save);
    }

    @Transactional
    public GroupDto save(GroupDto groupDto) {
        Group group = groupAdapter.toModel(groupDto);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());
        final Group save = groupRepository.save(group);
        return groupAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAll(Pageable pageable) {
        final Page<Group> subreddits = groupRepository.findAll(pageable);
        return subreddits.map(groupAdapter::toDto);
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllByTopPosting(int limit) {
        final List<Group> topPostings = groupRepository.findAllByTopPosting(Pageable.ofSize(limit));
        return topPostings.stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllGroupsByUserId(Long userId) {
        User user = userRepository.getById(userId);
        final List<Group> groups = user.getGroups();
        return groups.stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }
}
