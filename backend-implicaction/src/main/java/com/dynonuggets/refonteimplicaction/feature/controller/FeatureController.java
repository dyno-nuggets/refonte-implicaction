package com.dynonuggets.refonteimplicaction.feature.controller;

import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.feature.utils.FeatureUris.FEATURE_BASE_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(FEATURE_BASE_URI)
public class FeatureController {
    private final FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureDto>> getAll() {
        return ResponseEntity.ok(featureService.getAll());
    }
}
