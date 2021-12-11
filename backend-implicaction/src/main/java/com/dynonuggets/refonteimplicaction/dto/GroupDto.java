package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

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
    private List<UserDto> users;
}
