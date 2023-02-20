package com.dynonuggets.refonteimplicaction.community.rest.controller;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.rest.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.community.util.RelationUris.*;


@RestController
@AllArgsConstructor
@RequestMapping(RELATION_BASE_URI)
public class RelationController {

    private final RelationService relationService;
    private final AuthService authService;

    @GetMapping(GET_ALL_RELATIONS_URI)
    public ResponseEntity<Page<UserDto>> getAllFriends(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @PathVariable final Long userId
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Page<UserDto> userDtos = relationService.getAllFriendsByUserId(userId, pageable);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(GET_ALL_RELATIONS_REQUESTS_SENT_URI)
    public ResponseEntity<Page<UserDto>> getSentFriendRequest(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Long userId = authService.getCurrentUser().getId();
        final Page<UserDto> usersDto = relationService.getSentFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(GET_ALL_RELATIONS_REQUESTS_RECEIVED_URI)
    public ResponseEntity<Page<UserDto>> getReceivedFriendRequest(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        final Pageable pageable = PageRequest.of(page, rows);
        final Long userId = authService.getCurrentUser().getId();
        final Page<UserDto> usersDto = relationService.getReceivedFriendRequest(userId, pageable);
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping(REQUEST_RELATION)
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable final Long receiverId) {
        final User registredUser = authService.getCurrentUser();
        final RelationsDto relationsDto = relationService.requestRelation(registredUser.getId(), receiverId);
        return ResponseEntity.ok(relationsDto);
    }

    @DeleteMapping(DELETE_RELATION)
    public ResponseEntity<Void> deleteRelationBySender(@PathVariable final Long senderId) {
        final Long receiverId = authService.getCurrentUser().getId();
        relationService.deleteRelation(senderId, receiverId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(CANCEL_RELATION_REQUEST)
    public ResponseEntity<Void> cancelRelationByUser(@PathVariable("userId") final Long userId1) {
        final Long userId2 = authService.getCurrentUser().getId();
        relationService.cancelRelation(userId1, userId2);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(CONFIRM_RELATION)
    public ResponseEntity<RelationsDto> confirmRelation(@PathVariable final Long senderId) {
        final Long receiverId = authService.getCurrentUser().getId();
        final RelationsDto relation = relationService.confirmRelation(senderId, receiverId);
        return ResponseEntity.ok(relation);
    }
}
