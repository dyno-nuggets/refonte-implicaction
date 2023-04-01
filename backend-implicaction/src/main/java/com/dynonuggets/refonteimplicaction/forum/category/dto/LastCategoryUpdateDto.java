package com.dynonuggets.refonteimplicaction.forum.category.dto;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
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
    private ProfileDto author;
    private Instant createdAt;
    private long topicId;
    private Long responseId;
}
