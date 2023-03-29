package com.dynonuggets.refonteimplicaction.community.group.controller;

import com.dynonuggets.refonteimplicaction.community.group.dto.CreateGroupRequest;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupUris.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(GROUPS_BASE_URI)
public class GroupController {

    private final GroupService groupService;

    @ResponseBody
    @PostMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GroupDto> createGroup(
            @RequestPart final CreateGroupRequest request,
            @RequestParam final MultipartFile file
    ) {
        final GroupDto saveDto = groupService.createGroup(request, file);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @PostMapping(value = CREATE_NO_IMAGE)
    public ResponseEntity<GroupDto> createGroup(@RequestBody final CreateGroupRequest request) {
        final GroupDto saveDto = groupService.createGroup(request, null);
        return ResponseEntity.status(CREATED).body(saveDto);
    }

    @GetMapping(GET_VALIDATED_GROUPS_URI)
    public ResponseEntity<Page<GroupDto>> getAllEnabledGroups(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Direction.valueOf(sortOrder), sortBy));
        final Page<GroupDto> subredditDtos = groupService.getAllEnabledGroups(pageable);
        return ResponseEntity.ok(subredditDtos);
    }

    @PostMapping(SUBSCRIBE_GROUP)
    public ResponseEntity<Void> subscribeGroup(@PathVariable final Long groupId) {
        groupService.subscribeGroup(groupId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_PENDING_GROUP_URI)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<GroupDto>> getAllPendingGroups(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "rows", defaultValue = "10") final int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") final String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") final String sortOrder
    ) {
        final Pageable pageable = PageRequest.of(page, rows, Sort.by(Direction.valueOf(sortOrder), sortBy));
        final Page<GroupDto> pendingGroups = groupService.getAllPendingGroups(pageable);
        return ResponseEntity.ok(pendingGroups);
    }

    @PatchMapping(ENABLE_GROUP_URI)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GroupDto> enableGroup(@PathVariable final long groupId) {
        final GroupDto groupDto = groupService.enableGroup(groupId);
        return ResponseEntity.ok(groupDto);
    }
}
