package com.dynonuggets.refonteimplicaction.core.feature.repository;

import com.dynonuggets.refonteimplicaction.core.feature.model.FeatureModel;
import com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<FeatureModel, Long> {
    Optional<FeatureModel> findByFeatureKeyAndActiveTrue(FeatureKey key);
}
