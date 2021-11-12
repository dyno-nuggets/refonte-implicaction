package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.SubredditAdapter;
import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
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
public class SubredditService {

    private final SubredditAdapter subredditAdapter;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final CloudService cloudService;
    private final FileRepository fileRepository;

    @Transactional
    public SubredditDto save(MultipartFile file, SubredditDto subredditDto) {
        final FileModel image = cloudService.uploadImage(file);
        Subreddit subreddit = subredditAdapter.toModel(subredditDto);
        subreddit.setImage(image);
        subreddit.setCreatedAt(Instant.now());
        subreddit.setUser(authService.getCurrentUser());
        final Subreddit save = subredditRepository.save(subreddit);
        return subredditAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public Page<SubredditDto> getAll(Pageable pageable) {
        final Page<Subreddit> subreddits = subredditRepository.findAll(pageable);
        return subreddits.map(subredditAdapter::toDto);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAllByTopPosting(int limit) {
        final List<Subreddit> topPostings = subredditRepository.findAllByTopPosting(Pageable.ofSize(limit));
        return topPostings.stream()
                .map(subredditAdapter::toDto)
                .collect(toList());
    }
}
