package com.dynonuggets.refonteimplicaction.core.feature.controller;

import com.dynonuggets.refonteimplicaction.core.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.core.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/features")
public class FeatureController {
    private final FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureDto>> getAll() {
        return ResponseEntity.ok(featureService.getAll());
    }
}
