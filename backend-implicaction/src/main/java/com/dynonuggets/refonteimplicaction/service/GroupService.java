package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.SubredditAdapter;
import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GroupService {

    private final SubredditAdapter subredditAdapter;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final CloudService cloudService;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final SubredditRepository groupRepository;

    @Transactional
    public GroupDto save(MultipartFile image, GroupDto groupDto) {
        final FileModel fileModel = cloudService.uploadImage(image);
        final FileModel fileSave = fileRepository.save(fileModel);

        Group group = subredditAdapter.toModel(groupDto);
        group.setImage(fileSave);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());

        final Group save = subredditRepository.save(group);

        return subredditAdapter.toDto(save);
    }

    @Transactional
    public GroupDto save(GroupDto groupDto) {
        Group group = subredditAdapter.toModel(groupDto);
        group.setCreatedAt(Instant.now());
        group.setUser(authService.getCurrentUser());
        final Group save = subredditRepository.save(group);
        return subredditAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAll(Pageable pageable) {
        final Page<Group> subreddits = subredditRepository.findAll(pageable);
        return subreddits.map(subredditAdapter::toDto);
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllByTopPosting(int limit) {
        final List<Group> topPostings = subredditRepository.findAllByTopPosting(Pageable.ofSize(limit));
        return topPostings.stream()
                .map(subredditAdapter::toDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllGroupsByUserId(Long userId) {
        User user = userRepository.getById(userId);
        final List<Group> groups = user.getGroups();
        return groups.stream()
                .map(subredditAdapter::toDto)
                .collect(toList());
    }
}
