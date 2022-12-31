package com.dynonuggets.refonteimplicaction.adapter.forum;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateTopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.TopicDto;
import com.dynonuggets.refonteimplicaction.dto.forum.UpdateTopicDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.forum.Category;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@AllArgsConstructor
@Component
public class TopicAdapter {

    private final UserAdapter userAdapter;
    private final CategoryAdapter categoryAdapter;

    public Topic toModel(TopicDto dto, User user, Category category) {
        return Topic.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isLocked(dto.isLocked())
                .isPinned(dto.isPinned())
                .author(user)
                .category(category)
                .build();
    }

    public Topic toModel(CreateTopicDto dto, User user, Category category) {
        return Topic.builder()
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isLocked(dto.isLocked())
                .isPinned(dto.isPinned())
                .author(user)
                .category(category)
                .build();
    }

    public Topic mergeWith(Topic existingTopic, UpdateTopicDto dto, Category category) {
        if (dto.getTitle() != null) {
            existingTopic.setTitle(dto.getTitle());
        }

        if (dto.getMessage() != null) {
            existingTopic.setMessage(dto.getMessage());
        }

        if (!Objects.equals(category.getId(), existingTopic.getId())) {
            existingTopic.setCategory(category);
        }

        existingTopic.setLocked(dto.isLocked());
        existingTopic.setPinned(dto.isPinned());
        return existingTopic;
    }

    public TopicDto toDto(Topic model) {
        // /api/forum/topics/3
        return TopicDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .editedAt(model.getEditedAt())
                .isPinned(model.isPinned())
                .isLocked(model.isLocked())
                .author(userAdapter.toDto(model.getAuthor()))
                .category(categoryAdapter.toDtoWithoutChildren(model.getCategory()))
                .responses(new ArrayList<>())
                .build();
    }
}
