package com.dynonuggets.refonteimplicaction.dto.forum;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateTopicDto {
    private String title;
    private String message;
    private boolean isLocked;
    private boolean isPinned;
    private long categoryId;
}
