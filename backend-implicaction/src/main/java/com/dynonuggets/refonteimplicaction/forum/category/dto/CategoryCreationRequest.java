package com.dynonuggets.refonteimplicaction.forum.category.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryCreationRequest {
    private String title;
    private String description;
    private Long parentId;
}
