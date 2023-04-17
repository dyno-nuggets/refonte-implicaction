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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.feature.dto.enums.FeatureKey.EMAIL_NOTIFICATION;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {
    @Mock
    FeatureMapper mapper;
    @Mock
    FeatureRepository featureRepository;
    @InjectMocks
    FeatureService featureService;

    @Captor
    private ArgumentCaptor<FeatureModel> argumentCaptor;

    @Nested
    @DisplayName("# isActive")
    class IsActiveTest {
        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("doit retourner la valeur correspondante de l'activation de la feature")
        void should_return_true_or_false_when_feature_is_activated_or_not(final boolean isActivated) {
            // given
            given(featureRepository.findByFeatureKey(EMAIL_NOTIFICATION)).willReturn(of(FeatureModel.builder().active(isActivated).build()));

            // when
            final boolean isActive = featureService.isActive(EMAIL_NOTIFICATION);

            // then
            assertThat(isActive).isEqualTo(isActivated);
            verify(featureRepository, times(1)).findByFeatureKey(EMAIL_NOTIFICATION);
            verify(featureRepository, never()).save(any(FeatureModel.class));
        }

        @Test
        @DisplayName("doit retourner un FeatureModel nouvellement créé quand la feature n'existe pas encore en base de données")
        void should_return_new_unactive_feature_when_not_exists() {
            // given
            given(featureRepository.findByFeatureKey(EMAIL_NOTIFICATION)).willReturn(empty());
            given(featureRepository.save(any(FeatureModel.class))).willReturn(FeatureModel.builder().featureKey(EMAIL_NOTIFICATION).build());

            // when
            final boolean isActive = featureService.isActive(EMAIL_NOTIFICATION);

            // then
            verify(featureRepository, times(1)).findByFeatureKey(EMAIL_NOTIFICATION);
            verify(featureRepository, times(1)).save(argumentCaptor.capture());
            final FeatureModel createdFeature = argumentCaptor.getValue();
            assertThat(isActive).isFalse();
            assertThat(createdFeature).isNotNull();
            assertThat(createdFeature.getFeatureKey()).isEqualTo(EMAIL_NOTIFICATION);
            assertThat(createdFeature.isActive()).isFalse();

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
