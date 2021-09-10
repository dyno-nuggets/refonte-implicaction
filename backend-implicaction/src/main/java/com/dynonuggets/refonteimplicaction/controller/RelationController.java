package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.model.User;
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

    @PostMapping("{receiverId}")
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable("receiverId") Long receiverId) {
        final User registredUser = authService.getCurrentUser();
        RelationsDto relationsDto = relationService.requestRelation(registredUser.getId(), receiverId);
        return ResponseEntity.ok(relationsDto);
    }

    @GetMapping(value = "{userId}/confirmed", params = {"page", "size"})
    public ResponseEntity<List<RelationsDto>> getAllConfirmedRelations(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationsDto> relations = relationService.getAllConfirmedRelationsBySenderId(pageable, userId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping(value = "{userId}/pending", params = {"page", "size"})
    public ResponseEntity<List<RelationsDto>> getAllPendingRelations(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationsDto> relations = relationService.getAllPendingRelationsBySenderId(pageable, userId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping(value = "{userId}/received", params = {"page", "size"})
    public ResponseEntity<List<RelationsDto>> getAllReceivedRelations(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationsDto> relations = relationService.getAllPendingRelationsByReceiverId(pageable, userId);
        return ResponseEntity.ok(relations);
    }
}
