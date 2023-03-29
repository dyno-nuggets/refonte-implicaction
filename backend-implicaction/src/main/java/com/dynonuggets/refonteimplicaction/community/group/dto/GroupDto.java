package com.dynonuggets.refonteimplicaction.community.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private String creator;
    private String imageUrl;
    private boolean enabled;
    private Integer numberOfUsers;
}
