package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.Subreddit.BASE_URI;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(BASE_URI)
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        final SubredditDto saveDto = subredditService.save(subredditDto);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @GetMapping
    public ResponseEntity<Page<SubredditDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<SubredditDto> subredditDtos = subredditService.getAll(pageable);
        return ResponseEntity.ok(subredditDtos);
    }
}
