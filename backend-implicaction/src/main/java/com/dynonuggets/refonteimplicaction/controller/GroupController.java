package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(GROUPS_BASE_URI)
@AllArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @ResponseBody
    @PostMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GroupDto> createSubreddit(@RequestPart("group") GroupDto group, @RequestParam("file") MultipartFile image) {
        final GroupDto saveDto = groupService.save(image, group);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @PostMapping(CREATE_NO_IMAGE)
    public ResponseEntity<GroupDto> createSubreddit(@RequestBody GroupDto group) {
        final GroupDto saveDto = groupService.save(group);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @GetMapping(GET_VALIDATED_GROUPS_URI)
    public ResponseEntity<Page<GroupDto>> getAllValidGroups(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Direction.valueOf(sortOrder), sortBy));
        Page<GroupDto> subredditDtos = groupService.getAllValidGroups(pageable);
        return ResponseEntity.ok(subredditDtos);
    }

    @GetMapping(GET_ALL_BY_TOP_POSTING_URI)
    public ResponseEntity<List<GroupDto>> getAllByTopPosting(@RequestParam int limit) {
        List<GroupDto> groupDtos = groupService.getAllByTopPosting(limit);
        return ResponseEntity.ok(groupDtos);
    }

    @PostMapping(SUBSCRIBE_GROUP)
    public ResponseEntity<List<GroupDto>> subscribeGroup(@PathVariable final String groupName) {
        final List<GroupDto> groupDtos = groupService.addGroup(groupName);
        return ResponseEntity.ok(groupDtos);
    }

    @GetMapping(GET_PENDING_GROUP_URI)
    public ResponseEntity<Page<GroupDto>> getAllPendingGroups(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Direction.valueOf(sortOrder), sortBy));
        Page<GroupDto> pendingGroups = groupService.getAllPendingGroups(pageable);
        return ResponseEntity.ok(pendingGroups);
    }

    @PatchMapping(VALIDATE_GROUP_URI)
    public ResponseEntity<GroupDto> validateGroup(@PathVariable final String groupName) {
        final GroupDto groupDto = groupService.validateGroup(groupName);
        return ResponseEntity.ok(groupDto);
    }
}
