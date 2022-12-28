package com.dynonuggets.refonteimplicaction.dto.forum;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateCategoryDto {
    private String title;
    private String description;
    private Long parentId;
}
