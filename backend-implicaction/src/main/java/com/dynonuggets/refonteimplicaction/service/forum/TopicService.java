package com.dynonuggets.refonteimplicaction.service.forum;

import com.dynonuggets.refonteimplicaction.adapter.forum.TopicAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateTopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.TopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.UpdateTopicDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.forum.Category;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import com.dynonuggets.refonteimplicaction.repository.forum.CategoryRepository;
import com.dynonuggets.refonteimplicaction.repository.forum.TopicRepository;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.Message.CATEGORY_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.TOPIC_NOT_FOUND_MESSAGE;

@AllArgsConstructor
@Service
public class TopicService {
    private final AuthService authService;
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;
    private final TopicAdapter topicAdapter;

    @Transactional
    public Page<TopicDto> getTopicsFromCategory(long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId)));
        return topicRepository.findByCategory(category, pageable).map(topicAdapter::toDtoWithLastResponse);
    }

    @Transactional
    public TopicDto createTopic(CreateTopicDto topicDto) {
        Category category = categoryRepository.findById(topicDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, topicDto.getCategoryId())));
        Topic topic = topicAdapter.toModel(topicDto, authService.getCurrentUser(), category);
        Topic save = topicRepository.save(topic);
        return topicAdapter.toDto(save);
    }

    @Transactional
    public TopicDto updateTopic(UpdateTopicDto topicDto) {
        Category category = categoryRepository.findById(topicDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, topicDto.getCategoryId())));
        Topic existingTopic = findById(topicDto.getId());
        Topic topic = topicAdapter.mergeWith(existingTopic, topicDto, category);
        Topic save = topicRepository.save(topic);
        return topicAdapter.toDto(save);
    }

    @Transactional
    public TopicDto getTopic(long topicId) {
        Topic topic = findById(topicId);
        return topicAdapter.toDto(topic);
    }

    @Transactional
    public List<TopicDto> getLatest(int topicCount) {
        return topicRepository.findAllByOrderByLastActionDesc(PageRequest.of(0, topicCount))
                .map(topicAdapter::toDtoWithLastResponse)
                .getContent();
    }

    @Transactional
    public void deleteTopic(Long topicId) {
        Topic topic = findById(topicId);
        topicRepository.delete(topic);
    }

    private Topic findById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(String.format(TOPIC_NOT_FOUND_MESSAGE, topicId)));
    }
}
