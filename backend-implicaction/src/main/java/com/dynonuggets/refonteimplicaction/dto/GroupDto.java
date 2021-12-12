package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private String imageUrl;
    private Instant createdAt;
    private boolean valid;
    private Integer numberOfUsers;
    private String username;
    private Long userId;
}
