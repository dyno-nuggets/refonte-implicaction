package com.dynonuggets.refonteimplicaction.dto.forum;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
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
    private UserDto author;
    private TopicDto topic;
}
