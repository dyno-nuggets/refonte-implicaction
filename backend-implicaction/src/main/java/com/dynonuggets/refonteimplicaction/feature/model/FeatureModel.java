package com.dynonuggets.refonteimplicaction.feature.model;

import com.dynonuggets.refonteimplicaction.feature.model.properties.enums.FeatureKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feature")
public class FeatureModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "feature_key", nullable = false, unique = true)
    private FeatureKey featureKey;
    private boolean active;
}
