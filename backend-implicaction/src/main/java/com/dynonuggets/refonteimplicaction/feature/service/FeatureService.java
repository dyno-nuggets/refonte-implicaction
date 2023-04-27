package com.dynonuggets.refonteimplicaction.feature.service;

import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.mapper.FeatureMapper;
import com.dynonuggets.refonteimplicaction.feature.model.FeatureModel;
import com.dynonuggets.refonteimplicaction.feature.model.properties.enums.FeatureKey;
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
        final FeatureModel feature = featureRepository.findByFeatureKey(key)
                .orElseGet(() -> featureRepository.save(FeatureModel.builder().featureKey(key).build()));

        return feature.isActive();
    }

    public List<FeatureDto> getAll() {
        return featureRepository.findAll().stream()
                .map(featureMapper::toDto)
                .collect(toList());
    }
}
