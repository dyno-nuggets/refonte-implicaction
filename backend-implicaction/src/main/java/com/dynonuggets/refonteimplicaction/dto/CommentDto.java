package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.time.Instant;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private Instant createdAt;
    private String text;
    private String username;
}
