package com.dynonuggets.refonteimplicaction.forum.category.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UpdateCategoryRequest {
    private long id;
    private String title;
    private String description;
    private Long parentId;
}