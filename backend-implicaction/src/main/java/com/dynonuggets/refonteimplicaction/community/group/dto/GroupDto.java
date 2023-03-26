package com.dynonuggets.refonteimplicaction.community.group.dto;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private ProfileDto creator;
    private String imageUrl;
    private boolean enabled;
    private Integer numberOfUsers;
}
