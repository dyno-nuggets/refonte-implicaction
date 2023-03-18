package com.dynonuggets.refonteimplicaction.core.feature.model;

import com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "feature")
public class FeatureModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "feature_key", nullable = false, unique = true)
    private FeatureKey featureKey;
    private Boolean active;
}
