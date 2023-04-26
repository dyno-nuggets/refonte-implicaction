package com.dynonuggets.refonteimplicaction.forum.category.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.forum.category.adapter.CategoryAdapter;
import com.dynonuggets.refonteimplicaction.forum.category.domain.model.CategoryModel;
import com.dynonuggets.refonteimplicaction.forum.category.domain.repository.CategoryRepository;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryCreationRequest;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryDto;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryUpdateRequest;
import com.dynonuggets.refonteimplicaction.forum.commons.error.ForumException;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.forum.category.error.ForumCategoryErrorResult.*;
import static java.lang.String.valueOf;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;


@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final CategoryAdapter categoryAdapter;

    private final AuthService authService;

    @Transactional
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getCategories(final List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream()
                .map(categoryAdapter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getCategoriesWithLastUpdate(final List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream()
                .map(category -> {
                    // TODO: essayer de trouver comment facilement transformer ca en une seule requete
                    final TopicModel recentlyUpdatedTopic = topicRepository
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
    public CategoryDto getCategoryWithLastUpdate(final long id) {
        final CategoryModel category = getByIdIfExists(id);
        final TopicModel recentlyUpdatedTopic = topicRepository.findFirstByCategoryOrderByLastActionDesc(category)
                .orElse(null);
        return categoryAdapter.toDtoWithMostRecentlyUpdatedTopic(category, recentlyUpdatedTopic);
    }

    @Transactional
    public CategoryDto createCategory(final CategoryCreationRequest createRequest) throws ImplicactionException {
        authService.ensureCurrentUserAllowed(RoleEnum.ROLE_ADMIN);
        final CategoryModel createdCategory = categoryAdapter.toModel(createRequest);
        if (createRequest.getParentId() != null) {
            final CategoryModel parentCategory = getByIdIfExists(createRequest.getParentId());
            createdCategory.setParent(parentCategory);
        }

        final CategoryModel saved = categoryRepository.save(createdCategory);
        return categoryAdapter.toDto(saved);
    }

    public void deleteCategory(final long categoryId) {
        authService.ensureCurrentUserAllowed(RoleEnum.ROLE_ADMIN);
        final CategoryModel category = getByIdIfExists(categoryId);
        final boolean hasTopic = topicRepository.existsByCategory(category);
        final boolean hasChildren = isNotEmpty(category.getChildren());
        final boolean hasError = hasTopic || hasChildren;

        if (hasError) {
            throw new ForumException(hasChildren ? DELETE_CATEGORY_WITH_CHILD : DELETE_CATEGORY_WITH_TOPIC, valueOf(categoryId));
        }

        categoryRepository.delete(category);
    }

    public CategoryModel getByIdIfExists(final long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(FORUM_CATEGORY_NOT_FOUND, valueOf(categoryId)));
    }

    // TODO: vérifier les simplifications
    public CategoryDto editCategory(final CategoryUpdateRequest updateRequest) {
        authService.ensureCurrentUserAllowed(RoleEnum.ROLE_ADMIN);
        CategoryModel newParentCategory = null;
        // TODO: revoir la validation des champs
        if (updateRequest.getParentId() != null) {
            newParentCategory = getByIdIfExists(updateRequest.getParentId());
        }

        final CategoryModel category = getByIdIfExists(updateRequest.getId());
        if (newParentCategory != null) {
            CategoryModel parentCategory = newParentCategory;

            while (parentCategory != null && updateRequest.getId() != parentCategory.getId()) {
                parentCategory = parentCategory.getParent();
            }

            if (parentCategory != null) {
                throw new ForumException(CANNOT_ASSIGN_CHILD_CATEGORY_TO_ITSELF);
            }
        }

        category.setTitle(updateRequest.getTitle());
        category.setDescription(updateRequest.getDescription());
        category.setParent(newParentCategory);
        category.setChildren(category.getChildren());
        final CategoryModel editedCategory = categoryRepository.save(category);
        return categoryAdapter.toDto(editedCategory);
    }

}
