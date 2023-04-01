package com.dynonuggets.refonteimplicaction.forum.response.domain.repository;

import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<ResponseModel, Long> {
    Page<ResponseModel> findAllByTopic(TopicModel topic, Pageable pageable);
}
