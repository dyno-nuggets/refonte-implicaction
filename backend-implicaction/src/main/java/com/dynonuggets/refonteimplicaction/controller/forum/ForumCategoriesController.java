package com.dynonuggets.refonteimplicaction.controller.forum;

import com.dynonuggets.refonteimplicaction.dto.forum.CategoryDto;
import com.dynonuggets.refonteimplicaction.dto.forum.CreateCategoryDto;
import com.dynonuggets.refonteimplicaction.dto.forum.TopicDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.service.forum.CategoryService;
import com.dynonuggets.refonteimplicaction.service.forum.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(CATEGORY_BASE_URI)
@AllArgsConstructor
public class ForumCategoriesController {
    private final CategoryService categoryService;
    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() throws ImplicactionException {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryDto categoryDto) {
        CategoryDto saveDto = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @GetMapping(GET_CATEGORY_URI)
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId) {
        CategoryDto foundDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(foundDto);
    }

    @GetMapping(GET_TOPIC_FROM_CATEGORY_URI)
    public ResponseEntity<Page<TopicDto>> getTopicsFromCategory(
            @PathVariable long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<TopicDto> topicDtos = topicService.getTopicsFromCategory(categoryId, pageable);
        return ResponseEntity.ok(topicDtos);
    }
}
