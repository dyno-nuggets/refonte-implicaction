package com.dynonuggets.refonteimplicaction.feature.service;

import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.mapper.FeatureMapper;
import com.dynonuggets.refonteimplicaction.feature.model.FeatureModel;
import com.dynonuggets.refonteimplicaction.feature.repository.FeatureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.feature.model.enums.FeatureKey.EMAIL_NOTIFICATION;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @Mock
    FeatureMapper mapper;
    @Mock
    FeatureRepository featureRepository;
    @InjectMocks
    FeatureService featureService;

    @Nested
    @DisplayName("# isActive")
    class IsActiveTest {
        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("doit retourner la valeur correspondante de l'activation de la feature")
        void should_return_true_or_false_when_feature_is_activated_or_not(final boolean isActivated) {
            // given
            given(featureRepository.findByFeatureKeyAndActiveTrue(EMAIL_NOTIFICATION)).willReturn(isActivated ? of(FeatureModel.builder().build()) : empty());

            // when
            final boolean isActive = featureService.isActive(EMAIL_NOTIFICATION);

            // then
            assertThat(isActive).isEqualTo(isActivated);
        }
    }

    @Nested
    @DisplayName("# getAll")
    class GetAllTest {
        @Test
        @DisplayName("doit retourner la liste des features")
        void should_return_the_list_of_all_features() {
            // given
            final List<FeatureModel> features = List.of(FeatureModel.builder().id(1L).featureKey(EMAIL_NOTIFICATION).active(true).build());
            given(featureRepository.findAll()).willReturn(features);

            // when
            final List<FeatureDto> actualFeaturesDtos = featureService.getAll();

            // then
            assertThat(actualFeaturesDtos).size().isEqualTo(features.size());
        }
    }


}
