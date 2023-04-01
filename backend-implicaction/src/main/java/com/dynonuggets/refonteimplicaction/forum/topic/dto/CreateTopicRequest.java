package com.dynonuggets.refonteimplicaction.forum.topic.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateTopicRequest {
    private String title;
    private String message;
    private boolean isLocked;
    private boolean isPinned;
    private long categoryId;
}
