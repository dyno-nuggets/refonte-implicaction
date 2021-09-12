package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/relations")
public class RelationController {

    private final RelationService relationService;
    private final AuthService authService;

    @PostMapping("/request/{receiverId}")
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable("receiverId") Long receiverId) {
        final UserDto registredUser = authService.getCurrentUser();
        RelationsDto relationsDto = relationService.requestRelation(registredUser.getId(), receiverId);
        return ResponseEntity.ok(relationsDto);
    }

    @GetMapping(value = "/list/{userId}")
    public ResponseEntity<List<RelationsDto>> getAllForUserId(@PathVariable("userId") Long userId) {
        List<RelationsDto> relations = relationService.getAllForUserId(userId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping(value = "/pending/{userId}", params = {"page", "size"})
    public ResponseEntity<List<RelationsDto>> getAllPendingRelations(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationsDto> relations = relationService.getAllPendingBySenderId(pageable, userId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping(value = "/received/{userId}", params = {"page", "size"})
    public ResponseEntity<List<RelationsDto>> getAllReceivedRelations(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationsDto> relations = relationService.getAllPendingByReceiverId(pageable, userId);
        return ResponseEntity.ok(relations);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = "/{senderId}/decline")
    public ResponseEntity deleteRelationBySender(@PathVariable("senderId") Long senderId) {
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
    public ResponseEntity<RelationsDto> confirmRelation(
            @PathVariable("senderId") Long senderId
    ) {
        final Long receiverId = authService.getCurrentUser().getId();
        relationService.confirmRelation(senderId, receiverId);
        return ResponseEntity.noContent().build();
    }
}
