package com.dynonuggets.refonteimplicaction.forum.topic.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import com.dynonuggets.refonteimplicaction.forum.category.service.CategoryService;
import com.dynonuggets.refonteimplicaction.forum.topic.adapter.TopicAdapter;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.repository.TopicRepository;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicCreationRequest;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum.ROLE_ADMIN;
import static com.dynonuggets.refonteimplicaction.forum.topic.error.TopicErrorResult.TOPIC_NOT_FOUND;
import static java.lang.String.valueOf;


@AllArgsConstructor
@Service
public class TopicService {
    private final AuthService authService;
    private final TopicRepository topicRepository;
    private final TopicAdapter topicAdapter;
    private final ProfileService profileService;
    private final CategoryService categoryService;

    @Transactional
    public Page<TopicDto> getTopicsFromCategory(final long categoryId, final Pageable pageable) {
        final CategoryModel category = categoryService.getByIdIfExists(categoryId);
        return topicRepository.findByCategory(category, pageable)
                .map(topicAdapter::toDtoWithLastResponse);
    }

    @Transactional
    public TopicDto createTopic(final TopicCreationRequest createRequest) {
        final CategoryModel category = categoryService.getByIdIfExists(createRequest.getCategoryId());
        final TopicModel topic = topicAdapter.toModel(createRequest);
        topic.setAuthor(profileService.getCurrentProfile());
        topic.setCategory(category);
        final TopicModel save = topicRepository.save(topic);
        return topicAdapter.toDto(save);
    }

    @Transactional
    public TopicDto updateTopic(final TopicUpdateRequest updateRequest) {
        final CategoryModel category = categoryService.getByIdIfExists(updateRequest.getCategoryId());
        final TopicModel existingTopic = getByIdIfExists(updateRequest.getId());
        final String currentUsername = profileService.getCurrentProfile().getUser().getUsername();
        final String authorUsername = existingTopic.getAuthor().getUser().getUsername();

        if (!Objects.equals(currentUsername, authorUsername)) {
            authService.ensureCurrentUserAllowed(ROLE_ADMIN);
        }

        final TopicModel topic = topicAdapter.mergeWith(existingTopic, updateRequest, category);
        final TopicModel save = topicRepository.save(topic);
        return topicAdapter.toDto(save);
    }

    @Transactional
    public TopicDto getTopic(final long topicId) {
        final TopicModel topic = getByIdIfExists(topicId);
        return topicAdapter.toDto(topic);
    }

    @Transactional
    public List<TopicDto> getLatest(final int topicCount) {
        return topicRepository.findAllByOrderByLastActionDesc(PageRequest.of(0, topicCount))
                .map(topicAdapter::toDtoWithLastResponse)
                .getContent();
    }

    @Transactional
    public void deleteTopic(final Long topicId) {
        authService.ensureCurrentUserAllowed(ROLE_ADMIN);
        final TopicModel topic = getByIdIfExists(topicId);
        topicRepository.delete(topic);
    }

    public TopicModel getByIdIfExists(final Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(TOPIC_NOT_FOUND, valueOf(topicId)));
    }
}
