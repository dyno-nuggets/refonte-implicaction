package com.dynonuggets.refonteimplicaction.forum.topic.adapter;

import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.forum.category.adapter.CategoryAdapter;
import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import com.dynonuggets.refonteimplicaction.forum.response.adapter.ResponseAdapter;
import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicCreationRequest;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dynonuggets.refonteimplicaction.core.utils.DateUtils.getDurationAsString;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.CollectionUtils.size;

@AllArgsConstructor
@Lazy
@Component
public class TopicAdapter {

    @Lazy
    private final ProfileAdapter profileAdapter;

    @Lazy
    private final CategoryAdapter categoryAdapter;

    @Lazy
    private final ResponseAdapter responseAdapter;

    public TopicModel toModel(final TopicCreationRequest createRequest) {
        return TopicModel.builder()
                .title(createRequest.getTitle())
                .message(createRequest.getMessage())
                .isLocked(createRequest.isLocked())
                .isPinned(createRequest.isPinned())
                .build();
    }

    public TopicModel mergeWith(final TopicModel existingTopic, final TopicUpdateRequest dto, final CategoryModel category) {
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

    public TopicDto toDto(final TopicModel model) {
        if (model == null) {
            return null;
        }

        return TopicDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .editedAt(model.getEditedAt())
                .isPinned(model.isPinned())
                .isLocked(model.isLocked())
                .author(profileAdapter.toDto(model.getAuthor()))
                .category(categoryAdapter.toDtoWithoutChildren(model.getCategory()))
                .responses(new ArrayList<>())
                .lastResponse(null)
                .lastAction(model.getLastAction())
                .responsesCount(size(model.getResponses()))
                .durationAsString(getDurationAsString(model.getLastAction()))
                .build();
    }

    public TopicDto toDtoWithLastResponse(final TopicModel model) {
        final List<ResponseModel> responses = model.getResponses();
        return TopicDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .editedAt(model.getEditedAt())
                .isPinned(model.isPinned())
                .isLocked(model.isLocked())
                .author(profileAdapter.toDto(model.getAuthor()))
                .category(categoryAdapter.toDtoWithoutChildren(model.getCategory()))
                .responses(new ArrayList<>())
                .lastResponse(isNotEmpty(responses) ? responseAdapter.toDto(responses.get(responses.size() - 1)) : null)
                .lastAction(model.getLastAction())
                .responsesCount(model.getResponses().size())
                .durationAsString(getDurationAsString(model.getLastAction()))
                .build();
    }
}
