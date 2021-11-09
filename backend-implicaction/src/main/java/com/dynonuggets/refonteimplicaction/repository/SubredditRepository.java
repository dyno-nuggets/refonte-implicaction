package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Subreddit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    @Query("select s from Subreddit s order by s.posts.size desc")
    List<Subreddit> findAllByTopPosting(Pageable pageable);

    Optional<Subreddit> findByName(String name);
}
