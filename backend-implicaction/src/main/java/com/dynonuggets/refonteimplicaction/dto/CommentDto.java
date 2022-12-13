package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private String duration;
    private String text;
    private String username;
    private Long userId;
    private String userImageUrl;
    private Long groupId;

}
