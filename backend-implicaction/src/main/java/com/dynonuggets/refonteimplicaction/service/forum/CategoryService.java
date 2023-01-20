package com.dynonuggets.refonteimplicaction.service.forum;

import com.dynonuggets.refonteimplicaction.adapter.forum.CategoryAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.CategoryDto;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateCategoryDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.forum.Category;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import com.dynonuggets.refonteimplicaction.repository.forum.CategoryRepository;
import com.dynonuggets.refonteimplicaction.repository.forum.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.utils.Message.CATEGORY_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryAdapter categoryAdapter;
    private final TopicRepository topicRepository;

    @Transactional
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getCategories(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream()
                .map(categoryAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getCategoriesWithLastUpdate(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream()
                .map(category -> {
                    // TODO: essayer de trouver comment facilement transformer ca en une seule requete
                    Topic recentlyUpdatedTopic = topicRepository
                            .findFirstByCategoryOrderByLastActionDesc(category)
                            .orElse(null);
                    return categoryAdapter.toDtoWithMostRecentlyUpdatedTopic(category, recentlyUpdatedTopic);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getRootCategories() {
        return categoryRepository.findByParentIdIsNull().stream()
                .map(categoryAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto getCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, id)));

        return categoryAdapter.toDto(category);
    }

    @Transactional
    public CategoryDto getCategoryWithLastUpdate(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, id)));
        Topic recentlyUpdatedTopic = topicRepository
                .findFirstByCategoryOrderByLastActionDesc(category)
                .orElse(null);
        return categoryAdapter.toDtoWithMostRecentlyUpdatedTopic(category, recentlyUpdatedTopic);
    }

    @Transactional
    public CategoryDto createCategory(CreateCategoryDto categoryDto) throws ImplicactionException {
        Category createdCategory = categoryAdapter.toModel(categoryDto);
        if (categoryDto.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryDto.getParentId())));
            createdCategory.setParent(parentCategory);
        }

        Category saved = categoryRepository.save(createdCategory);
        return categoryAdapter.toDto(saved);
    }
}
