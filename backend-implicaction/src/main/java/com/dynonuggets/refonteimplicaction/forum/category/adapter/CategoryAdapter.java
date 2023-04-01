package com.dynonuggets.refonteimplicaction.forum.category.adapter;

import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryDto;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CreateCategoryRequest;
import com.dynonuggets.refonteimplicaction.forum.category.dto.UpdateCategoryRequest;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Lazy
@Component
public class CategoryAdapter {

    @Lazy
    private final LastCategoryUpdateAdapter lastCategoryUpdateAdapter;

    public CategoryModel toModel(final CategoryDto dto) {
        if (dto == null) {
            return null;
        }

        return CategoryModel.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public CategoryModel toModel(final CreateCategoryRequest dto) {
        if (dto == null) {
            return null;
        }

        return CategoryModel.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public CategoryModel toModel(final UpdateCategoryRequest dto, final CategoryModel parent) {
        if (dto == null) {
            return null;
        }

        return CategoryModel.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .parent(parent)
                .build();
    }

    public CategoryDto toDtoWithoutChildren(final CategoryModel model) {
        if (model == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(callIfNotNull(model.getParent(), CategoryModel::getId))
                .build();
    }

    public CategoryDto toDto(final CategoryModel model) {
        if (model == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(callIfNotNull(model.getParent(), CategoryModel::getId))
                .children(emptyStreamIfNull(model.getChildren()).map(CategoryModel::getId).collect(toList()))
                .build();
    }

    public CategoryDto toDtoWithMostRecentlyUpdatedTopic(final CategoryModel model, final TopicModel recentlyUpdatedTopic) {
        if (model == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .parentId(callIfNotNull(model.getParent(), CategoryModel::getId))
                .children(emptyStreamIfNull(model.getChildren()).map(CategoryModel::getId).collect(toList()))
                .lastUpdate(lastCategoryUpdateAdapter.toDto(recentlyUpdatedTopic))
                .build();
    }
}
