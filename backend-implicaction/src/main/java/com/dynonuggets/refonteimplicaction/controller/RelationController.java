package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/relations")
public class RelationController {

    private final RelationService relationService;
    private final AuthService authService;

    @PostMapping("/request/{receiverId}")
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable("receiverId") Long receiverId) {
        final User registredUser = authService.getCurrentUser();
        RelationsDto relationsDto = relationService.requestRelation(registredUser.getId(), receiverId);
        return ResponseEntity.ok(relationsDto);
    }

    @DeleteMapping(value = "/{senderId}/decline")
    public ResponseEntity<Void> deleteRelationBySender(@PathVariable("senderId") Long senderId) {
        final Long receiverId = authService.getCurrentUser().getId();
        relationService.deleteRelation(senderId, receiverId);
        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = "/{userId}/cancel")
    public ResponseEntity cancelRelationByUser(@PathVariable("userId") Long userId1) {
        final Long userId2 = authService.getCurrentUser().getId();
        relationService.cancelRelation(userId1, userId2);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{senderId}/confirm")
    public ResponseEntity<RelationsDto> confirmRelation(@PathVariable("senderId") Long senderId) {
        final Long receiverId = authService.getCurrentUser().getId();
        RelationsDto relation = relationService.confirmRelation(senderId, receiverId);
        return ResponseEntity.ok(relation);
    }
}
