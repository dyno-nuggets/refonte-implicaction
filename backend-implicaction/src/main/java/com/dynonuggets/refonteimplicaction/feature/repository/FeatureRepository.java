package com.dynonuggets.refonteimplicaction.feature.repository;

import com.dynonuggets.refonteimplicaction.feature.model.FeatureModel;
import com.dynonuggets.refonteimplicaction.feature.model.properties.enums.FeatureKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<FeatureModel, Long> {
    Optional<FeatureModel> findByFeatureKey(FeatureKey key);
}
