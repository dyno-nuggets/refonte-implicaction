package com.dynonuggets.refonteimplicaction.dto.forum;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LastCategoryUpdateDto {
    private String type;
    private String title;
    private UserDto author;
    private Instant createdAt;
    private long topicId;
    private Long responseId;
}
