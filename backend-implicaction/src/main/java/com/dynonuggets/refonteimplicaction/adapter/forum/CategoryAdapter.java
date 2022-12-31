package com.dynonuggets.refonteimplicaction.adapter.forum;

import com.dynonuggets.refonteimplicaction.dto.forum.CategoryDto;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateCategoryDto;
import com.dynonuggets.refonteimplicaction.model.forum.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryAdapter {
    public Category toModel(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public Category toModel(CreateCategoryDto dto) {
        return Category.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public CategoryDto toDtoWithoutChildren(Category model) {
        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(model.getParent() != null ? model.getParent().getId() : null)
                .build();
    }

    public CategoryDto toDto(Category model) {
        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(model.getParent() != null ? model.getParent().getId() : null)
                .children(model.getChildren().stream().map(Category::getId).collect(Collectors.toList()))
                .build();
    }
}
