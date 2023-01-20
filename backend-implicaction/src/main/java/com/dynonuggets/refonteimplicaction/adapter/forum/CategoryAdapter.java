package com.dynonuggets.refonteimplicaction.adapter.forum;

import com.dynonuggets.refonteimplicaction.dto.forum.CategoryDto;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateCategoryDto;
import com.dynonuggets.refonteimplicaction.model.forum.Category;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Lazy
@Component
public class CategoryAdapter {
    @Lazy
    private final TopicAdapter topicAdapter;

    @Lazy
    private final LastCategoryUpdateAdapter lastCategoryUpdateAdapter;

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

    public CategoryDto toDtoWithMostRecentlyUpdatedTopic(Category model, Topic recentlyUpdatedTopic) {
        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(model.getParent() != null ? model.getParent().getId() : null)
                .children(
                        model.getChildren().stream()
                                .map(Category::getId)
                                .collect(Collectors.toList())
                )
                .lastUpdate(lastCategoryUpdateAdapter.toDto(recentlyUpdatedTopic))
                .build();
    }
}
