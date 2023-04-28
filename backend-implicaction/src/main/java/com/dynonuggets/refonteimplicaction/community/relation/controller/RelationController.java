package com.dynonuggets.refonteimplicaction.community.relation.controller;

import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationCreationRequest;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationUris.*;


@RestController
@AllArgsConstructor
@RequestMapping(RELATION_BASE_URI)
public class RelationController {

    private final RelationService relationService;

    @PostMapping
    public ResponseEntity<RelationsDto> createRelation(@Valid @RequestBody final RelationCreationRequest createRequest) {
        return ResponseEntity.ok(relationService.requestRelation(createRequest.getSender(), createRequest.getReceiver()));
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
