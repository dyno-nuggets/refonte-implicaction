package com.dynonuggets.refonteimplicaction.forum.category.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryDto {
    private Long id;
    private String title;
    private String description;
    private Long parentId;
    private List<Long> children;
    private LastCategoryUpdateDto lastUpdate;
}
