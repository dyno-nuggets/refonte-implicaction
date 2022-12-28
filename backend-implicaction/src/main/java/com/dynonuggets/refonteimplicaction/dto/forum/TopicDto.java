package com.dynonuggets.refonteimplicaction.dto.forum;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
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
    private UserDto author;
    private List<ResponseDto> responses;
    private CategoryDto category;
}
