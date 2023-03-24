package com.dynonuggets.refonteimplicaction.community.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
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
