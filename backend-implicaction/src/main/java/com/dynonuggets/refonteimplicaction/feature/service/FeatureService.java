package com.dynonuggets.refonteimplicaction.feature.service;

import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.mapper.FeatureMapper;
import com.dynonuggets.refonteimplicaction.feature.model.enums.FeatureKey;
import com.dynonuggets.refonteimplicaction.feature.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;

    public boolean isActive(final FeatureKey key) {
        return featureRepository.findByFeatureKeyAndActiveTrue(key).isPresent();
    }

    public List<FeatureDto> getAll() {
        return featureRepository.findAll().stream()
                .map(featureMapper::toDto)
                .collect(toList());
    }
}
