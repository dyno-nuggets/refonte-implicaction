package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.SubredditAdapter;
import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditAdapter subredditAdapter;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditAdapter.toModel(subredditDto);
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
}
