package com.dynonuggets.refonteimplicaction.forum.response.dto;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResponseDto {
    private long id;
    private String message;
    private Instant createdAt;
    private Instant editedAt;
    private ProfileDto author;
    private TopicDto topic;
}
