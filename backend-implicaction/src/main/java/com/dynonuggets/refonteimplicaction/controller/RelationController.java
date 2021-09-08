package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/relations")
public class RelationController {

    private final RelationService relationService;


    @PostMapping("{receiverId}")
    public ResponseEntity<RelationsDto> requestRelation(@PathVariable("receiverId") Long receiverId) {
        RelationsDto relationsDto = relationService.requestRelation(receiverId);
        return ResponseEntity.ok(relationsDto);
    }
}
