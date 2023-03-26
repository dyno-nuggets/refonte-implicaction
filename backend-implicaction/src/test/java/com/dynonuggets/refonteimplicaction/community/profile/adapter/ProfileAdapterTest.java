package com.dynonuggets.refonteimplicaction.community.profile.adapter;

import com.dynonuggets.refonteimplicaction.community.group.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.training.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.community.workexperience.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.generateRandomProfile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileAdapterTest {
    @Mock
    WorkExperienceAdapter workExperienceAdapter;
    @Mock
    TrainingAdapter trainingAdapter;
    @Mock
    GroupAdapter groupAdapter;
    @Mock
    FileService fileService;
    @InjectMocks
    ProfileAdapter profileAdapter;

    @Nested
    @DisplayName("# toDto")
    class ToDtoTest {
        @Test
        @DisplayName("doit créer un dto quand toDto est appelé et que le model n'est pas null")
        void should_map_to_dto_when_toDto_and_model_is_not_null() {
            // given
            final ProfileModel profile = generateRandomProfile();

            // when
            final ProfileDto profileDto = profileAdapter.toDto(profile);

            // then
            assertThat(profileDto.getUsername()).isEqualTo(profile.getUser().getUsername());
            assertThat(profileDto.getEmail()).isEqualTo(profile.getUser().getEmail());
            assertThat(profileDto.getAvatar()).isNotEmpty();
            assertThat(profileDto.getBirthday()).isEqualTo(profile.getUser().getBirthday());
            assertThat(profileDto.getHobbies()).isEqualTo(profile.getHobbies());
            assertThat(profileDto.getPurpose()).isEqualTo(profile.getPurpose());
            assertThat(profileDto.getPresentation()).isEqualTo(profile.getPresentation());
            assertThat(profileDto.getExpectation()).isEqualTo(profile.getExpectation());
            assertThat(profileDto.getContribution()).isEqualTo(profile.getContribution());
            assertThat(profileDto.getPhoneNumber()).isEqualTo(profile.getPhoneNumber());
            assertThat(profileDto.getExperiences()).isNotNull();
            assertThat(profileDto.getTrainings()).isNotNull();
            assertThat(profileDto.getGroups()).isNotNull();

            verify(workExperienceAdapter, times(1)).toDto(any());
            verify(trainingAdapter, times(1)).toDto(any());
            verify(groupAdapter, times(1)).toDto(any());
            verify(fileService, times(1)).buildFileUri(any());
        }

        @Test
        @DisplayName("doit renvoyer null quand le model est null")
        void should_return_null_when_toDto_and_model_is_null() {
            assertThat(profileAdapter.toDto(null)).isNull();
        }
    }

    @Nested
    @DisplayName("# toDtoLight")
    class ToDtoLightTest {
        @Test
        @DisplayName("doit retourner un dto_light quand toDtoLight est appelé et que le model n'est pas nul")
        void should_return_dto_light_when_toDtoLight_and_model_is_not_null() {
            // given
            final ProfileModel profile = generateRandomProfile();

            // when
            final ProfileDto profileDto = profileAdapter.toDtoLight(profile);

            // then
            assertThat(profileDto.getUsername()).isEqualTo(profile.getUser().getUsername());
            assertThat(profileDto.getEmail()).isEqualTo(profile.getUser().getEmail());
            assertThat(profileDto.getAvatar()).isNotEmpty();
            assertThat(profileDto.getBirthday()).isNull();
            assertThat(profileDto.getHobbies()).isNull();
            assertThat(profileDto.getPurpose()).isNull();
            assertThat(profileDto.getPresentation()).isNull();
            assertThat(profileDto.getExpectation()).isNull();
            assertThat(profileDto.getContribution()).isNull();
            assertThat(profileDto.getPhoneNumber()).isNull();

            verifyNoInteractions(workExperienceAdapter);
            verifyNoInteractions(trainingAdapter);
            verifyNoInteractions(groupAdapter);
        }

        @Test
        @DisplayName("doit renvoyer null quand le model est null")
        void should_return_null_when_toDtoLight_and_model_is_null() {
            assertThat(profileAdapter.toDtoLight(null)).isNull();
        }
    }
}
