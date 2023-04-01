package com.dynonuggets.refonteimplicaction.forum.category.adapter;

import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.forum.category.dto.LastCategoryUpdateDto;
import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Lazy
@Component
public class LastCategoryUpdateAdapter {

    private final ProfileAdapter profileAdapter;

    public LastCategoryUpdateDto toDto(final TopicModel topic) {
        final List<ResponseModel> responseList = topic.getResponses();
        final ResponseModel lastResponse = !responseList.isEmpty()
                ? responseList.get(responseList.size() - 1)
                : null;
        final boolean hasResponse = lastResponse != null;
        return LastCategoryUpdateDto.builder()
                .title(topic.getTitle())
                .topicId(topic.getId())
                .createdAt(hasResponse ? lastResponse.getCreatedAt() : topic.getCreatedAt())
                .responseId(hasResponse ? lastResponse.getId() : null)
                .type(hasResponse ? "response" : "topic")
                .author(profileAdapter.toDtoLight(topic.getAuthor()))
                .build();
    }
}
