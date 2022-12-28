package com.dynonuggets.refonteimplicaction.repository.forum;

import com.dynonuggets.refonteimplicaction.model.forum.Response;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findAllByTopic(Topic topic, Pageable pageable);
}
