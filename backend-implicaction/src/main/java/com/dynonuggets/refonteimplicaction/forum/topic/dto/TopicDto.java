package com.dynonuggets.refonteimplicaction.forum.topic.dto;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryDto;
import com.dynonuggets.refonteimplicaction.forum.response.dto.ResponseDto;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TopicDto {
    private long id;
    private String title;
    private String message;
    private Instant createdAt;
    private Instant editedAt;
    private boolean isLocked;
    private boolean isPinned;
    private ProfileDto author;
    private List<ResponseDto> responses;
    private CategoryDto category;
    private Instant lastAction;
    private String durationAsString;
    private long responsesCount;
    private ResponseDto lastResponse;
}
