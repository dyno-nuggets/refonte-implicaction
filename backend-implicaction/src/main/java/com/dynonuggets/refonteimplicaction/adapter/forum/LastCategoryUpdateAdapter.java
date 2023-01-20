package com.dynonuggets.refonteimplicaction.adapter.forum;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.LastCategoryUpdateDto;
import com.dynonuggets.refonteimplicaction.model.forum.Response;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Lazy
@Component
public class LastCategoryUpdateAdapter {

    private final UserAdapter userAdapter;

    public LastCategoryUpdateDto toDto(Topic topic) {
        List<Response> responseList = topic.getResponses();
        Response lastResponse = !responseList.isEmpty()
                ? responseList.get(responseList.size() - 1)
                : null;
        boolean hasResponse = lastResponse != null;
        return LastCategoryUpdateDto.builder()
                .title(topic.getTitle())
                .topicId(topic.getId())
                .createdAt(hasResponse ? lastResponse.getCreatedAt() : topic.getCreatedAt())
                .responseId(hasResponse ? lastResponse.getId() : null)
                .type(hasResponse ? "response" : "topic")
                .author(userAdapter.toDto(topic.getAuthor()))
                .build();
    }
}
