package com.dynonuggets.refonteimplicaction.forum.category.controller;

import com.dynonuggets.refonteimplicaction.core.dto.ExceptionResponse;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CategoryDto;
import com.dynonuggets.refonteimplicaction.forum.category.dto.CreateCategoryRequest;
import com.dynonuggets.refonteimplicaction.forum.category.dto.UpdateCategoryRequest;
import com.dynonuggets.refonteimplicaction.forum.category.service.CategoryService;
import com.dynonuggets.refonteimplicaction.forum.topic.dto.TopicDto;
import com.dynonuggets.refonteimplicaction.forum.topic.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.forum.category.utils.ForumCategoryUris.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(CATEGORY_BASE_URI)
public class ForumCategoriesController {
    private final CategoryService categoryService;
    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "false") final boolean onlyRoot) throws ImplicactionException {
        if (onlyRoot) {
            return ResponseEntity.ok(categoryService.getRootCategories());
        }
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody final CreateCategoryRequest createRequest) {
        final CategoryDto saveDto = categoryService.createCategory(createRequest);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @GetMapping(GET_CATEGORY_URI)
    public ResponseEntity<List<CategoryDto>> getCategory(@PathVariable final List<Long> categoryIds, @RequestParam(defaultValue = "false") final boolean withRecentlyUpdatedTopic) {
        final List<CategoryDto> categories = withRecentlyUpdatedTopic
                ? categoryService.getCategoriesWithLastUpdate(categoryIds)
                : categoryService.getCategories(categoryIds);
        return ResponseEntity.ok(categories);
    }

    @GetMapping(GET_TOPIC_FROM_CATEGORY_URI)
    public ResponseEntity<Page<TopicDto>> getTopicsFromCategory(
            @PathVariable final long categoryId,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        final Page<TopicDto> topicDtos = topicService.getTopicsFromCategory(categoryId, pageable);
        return ResponseEntity.ok(topicDtos);
    }

    @DeleteMapping(value = DELETE_CATEGORY_URI)
    public ResponseEntity<ExceptionResponse> delete(@PathVariable("categoryId") final long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<CategoryDto> edit(@RequestBody final UpdateCategoryRequest updateRequest) {

        final CategoryDto editDto = categoryService.editCategory(updateRequest);
        return ResponseEntity.status(CREATED).body(editDto);

    }
}
