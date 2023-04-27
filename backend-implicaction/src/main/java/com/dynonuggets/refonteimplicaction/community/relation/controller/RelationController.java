package com.dynonuggets.refonteimplicaction.community.relation.controller;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.service.RelationService;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationUris.*;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static org.springframework.data.domain.PageRequest.of;


@RestController
@AllArgsConstructor
@RequestMapping(RELATION_BASE_URI)
public class RelationController {

    private final RelationService relationService;
    private final AuthService authService;

    @GetMapping(GET_ALL_RELATIONS_URI)
    public ResponseEntity<Page<RelationsDto>> getAllRelationsByUsername(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @PathVariable final String username
    ) {
        return ResponseEntity.ok(relationService.getAllRelationsByUsername(username, of(page, rows)));
    }

    @GetMapping(GET_ALL_COMMUNITY)
    public ResponseEntity<Page<RelationsDto>> getAllCommunity(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows
    ) {
        return ResponseEntity.ok(relationService.getAllCommunity(of(page, rows)));
    }

    @GetMapping(GET_ALL_RELATIONS_REQUESTS_SENT_URI)
    public ResponseEntity<Page<RelationsDto>> getSentFriendRequest(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @PathVariable final String username
    ) {
        return ResponseEntity.ok(relationService.getSentFriendRequest(username, of(page, rows)));
    }

    @GetMapping(GET_ALL_RELATIONS_REQUESTS_RECEIVED_URI)
    public ResponseEntity<Page<RelationsDto>> getReceivedFriendRequest(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int rows,
            @PathVariable final String username
    ) {
        return ResponseEntity.ok(relationService.getReceivedFriendRequest(username, of(page, rows)));
    }

    @PostMapping(REQUEST_RELATION)
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable final String receiverName) {
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        return ResponseEntity.ok(relationService.requestRelation(currentUsername, receiverName));
    }

    @DeleteMapping(REMOVE_RELATION)
    public ResponseEntity<Void> removeRelation(@PathVariable final Long relationId) {
        relationService.removeRelation(relationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(CONFIRM_RELATION)
    public ResponseEntity<RelationsDto> confirmRelation(@PathVariable final Long relationId) {
        final RelationsDto relation = relationService.confirmRelation(relationId);
        return ResponseEntity.ok(relation);
    }
}
