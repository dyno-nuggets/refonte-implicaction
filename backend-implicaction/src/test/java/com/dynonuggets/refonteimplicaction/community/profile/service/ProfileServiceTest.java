package com.dynonuggets.refonteimplicaction.community.profile.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.profile.mapper.ProfileMapper;
import com.dynonuggets.refonteimplicaction.community.relation.domain.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.community.relation.mapper.RelationMapper;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.error.TechnicalException;
import com.dynonuggets.refonteimplicaction.core.service.UserService;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.community.profile.error.ProfileErrorResult.PROFILE_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_ALREADY_EXISTS_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.core.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.utils.UserTestUtils.generateRandomUserModel;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static java.lang.String.format;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    AuthService authService;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    RelationRepository relationRepository;
    @Mock
    ProfileMapper profileMapper;
    @Mock
    CloudService cloudService;
    @Mock
    UserService userService;
    @Mock
    RelationMapper relationMapper;
    @InjectMocks
    ProfileService profileService;

    @Captor
    ArgumentCaptor<ProfileModel> profileCaptor;

    @Nested
    @DisplayName("# createProfile")
    class CreateProfileTests {
        @Test
        @DisplayName("doit créer un profil utilisateur si l'utilisateur existe et que son profil n'existe pas encore")
        void should_create_profile_if_user_exists_and_profile_is_not_already_created() {
            // given
            final UserModel userModel = generateRandomUserModel();
            final String username = userModel.getUsername();
            given(userService.getUserByUsernameIfExists(username)).willReturn(userModel);
            given(profileRepository.findByUser_Username(username)).willReturn(Optional.empty());

            // when
            profileService.createProfile(username);

            // then
            verify(userService, times(1)).getUserByUsernameIfExists(username);
            verify(profileRepository, times(1)).save(any(ProfileModel.class));
            verify(profileRepository, times(1)).save(profileCaptor.capture());
            final ProfileModel profile = profileCaptor.getValue();
            assertThat(profile.getUser()).isEqualTo(userModel);
        }

        @Test
        @DisplayName("doit lancer une exception si l'utilisateur n'existe pas")
        void should_throw_exception_if_user_not_exists() {
            // given
            final String username = "username";
            given(userService.getUserByUsernameIfExists(username)).willThrow(new EntityNotFoundException(USERNAME_NOT_FOUND, username));

            // when
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.createProfile("username"));
            assertImplicactionException(e, EntityNotFoundException.class, USERNAME_NOT_FOUND, username);
            verifyNoInteractions(profileRepository);
        }


        @Test
        @DisplayName("doit lancer une exception si le profil existe déjà")
        void should_throw_exception_if_profile_already_exists() {
            // given
            final UserModel userModel = generateRandomUserModel();
            final String username = userModel.getUsername();
            given(userService.getUserByUsernameIfExists(username)).willReturn(userModel);
            given(profileRepository.findByUser_Username(username)).willReturn(Optional.of(ProfileModel.builder().build()));

            // when
            final TechnicalException exception = assertThrows(TechnicalException.class, () -> profileService.createProfile(username));
            assertThat(exception.getMessage()).isEqualTo(format(PROFILE_ALREADY_EXISTS_MESSAGE, username));
            verify(profileRepository, times(1)).findByUser_Username(username);
            verifyNoMoreInteractions(profileRepository);
        }
    }

    @Nested
    @DisplayName("# getByUsernameIfExistsAndUserEnabled")
    class GetByUsernameIfExistsTest {
        @Test
        @DisplayName("doit retourner un utilisateur quand getByUsernameIfExists est appelé et qu'un utilisateur existe pour le nom d'utilisateur fourni")
        void should_return_profile_when_getByUsernameIfExists_and_username_exists() {
            // given
            final ProfileModel expectedProfile = generateRandomProfile();
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(any())).willReturn(Optional.of(expectedProfile));

            // when
            final ProfileModel actualProfile = profileService.getByUsernameIfExistsAndUserEnabled(expectedProfile.getUser().getUsername());

            assertThat(expectedProfile)
                    .usingRecursiveComparison()
                    .isEqualTo(actualProfile);
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand getByUsernameIfExists est appelé mais que l'utilisateur n'existe pas")
        void should_throw_exception_when_getByUsernameIfExists_and_username_not_exists() {
            // given
            final String username = "usernameInexistant";
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(any())).willThrow(new EntityNotFoundException(PROFILE_NOT_FOUND, username));

            // when - then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.getByUsernameIfExistsAndUserEnabled(username));
            assertImplicactionException(e, EntityNotFoundException.class, PROFILE_NOT_FOUND, username);
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
        }
    }

    @Nested
    @DisplayName("# getByUsername")
    class GetByUsernameTest {
        @Test
        @DisplayName("doit retourner le ProfileDto correspondant quand getByUsername est appelé et qu'un utilisateur existe pour le nom d'utilisateur fourni")
        void should_return_ProfileDto_when_getByUsername_and_username_exists() {
            // given
            final ProfileModel expectedProfile = generateRandomProfile();
            final ProfileDto expectedProfileDto = ProfileDto.builder().username(expectedProfile.getUser().getUsername()).build();
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(any())).willReturn(Optional.of(expectedProfile));
            given(profileMapper.toDto(any())).willReturn(expectedProfileDto);

            // when
            final ProfileDto profileDto = profileService.getByUsername(expectedProfile.getUser().getUsername());

            // then
            assertThat(profileDto.getUsername()).isEqualTo(expectedProfile.getUser().getUsername());
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand getByUsername est appelé mais que le nom d'utilisateur n'existe pas")
        void should_throw_exception_when_getByUsername_but_username_not_exists() {
            final String username = "usernameInexistant";
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(any())).willThrow(new EntityNotFoundException(PROFILE_NOT_FOUND, username));

            // when - then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.getByUsername(username));
            assertImplicactionException(e, EntityNotFoundException.class, PROFILE_NOT_FOUND, username);
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
        }
    }

    @Nested
    @DisplayName("# updateProfile")
    class UpdateProfileTest {
        @Test
        @DisplayName("doit retourner le profileDto modifié quand updateProfile est appelée")
        void should_return_modified_profileDto_when_updateProfile() {
            // given
            final ProfileDto expectedProfileDto = generateRandomProfileDto();
            final ProfileUpdateRequest updateRequest = generateRandomProfileUpdateRequest(expectedProfileDto.getUsername());
            final ProfileModel profile = ProfileModel.builder().user(UserModel.builder().build()).build();
            willDoNothing().given(authService).verifyAccessIsGranted(any());
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(any())).willReturn(Optional.of(profile));
            given(profileRepository.save(any())).willReturn(profile);
            given(profileMapper.toDto(any())).willReturn(expectedProfileDto);
            expectedProfileDto.setBirthday(updateRequest.getBirthday());
            expectedProfileDto.setHobbies(updateRequest.getHobbies());
            expectedProfileDto.setPurpose(updateRequest.getPurpose());
            expectedProfileDto.setPresentation(updateRequest.getPresentation());
            expectedProfileDto.setExpectation(updateRequest.getExpectation());
            expectedProfileDto.setContribution(updateRequest.getContribution());
            expectedProfileDto.setPhoneNumber(updateRequest.getPhoneNumber());

            // when
            final ProfileDto actualProfileDto = profileService.updateProfile(updateRequest);

            // then
            assertThat(actualProfileDto).isEqualTo(expectedProfileDto);
            verify(authService, times(1)).verifyAccessIsGranted(any());
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
            verify(profileRepository, times(1)).save(any());
            verify(profileMapper, times(1)).toDto(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand updateProfile est lancé pour un profil différent de l'utilisateur courant")
        void should_throw_exception_when_profile_to_update_does_not_belong_to_current_user() {
            // given
            final ProfileUpdateRequest updateRequest = generateRandomProfileUpdateRequest();
            willThrow(new CoreException(OPERATION_NOT_PERMITTED)).given(authService).verifyAccessIsGranted(any());

            // when - then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.updateProfile(updateRequest));
            assertImplicactionException(e, CoreException.class, OPERATION_NOT_PERMITTED);
            verify(authService, times(1)).verifyAccessIsGranted(any());
            verifyNoInteractions(profileRepository);
            verifyNoInteractions(profileMapper);
        }

        @Test
        @DisplayName("doit lancer une exception quand updateProfile est lancé et que l'utilisateur courant est null")
        void should() {
            // given
            final ProfileUpdateRequest updateRequest = generateRandomProfileUpdateRequest();
            willThrow(new CoreException(OPERATION_NOT_PERMITTED)).given(authService).verifyAccessIsGranted(any());

            // when - then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.updateProfile(updateRequest));
            assertImplicactionException(e, CoreException.class, OPERATION_NOT_PERMITTED);
            verify(authService, times(1)).verifyAccessIsGranted(any());
            verifyNoInteractions(profileRepository);
            verifyNoInteractions(profileMapper);
        }
    }

    @Test
    @DisplayName("doit retourner la liste des profils des utilisateurs")
    void should_get_all_profiles_when_getAllProfiles() {
        final Page<ProfileModel> profiles = new PageImpl<>(of(generateRandomProfile(), generateRandomProfile(), generateRandomProfile()));
        given(profileRepository.findAll(any(Pageable.class))).willReturn(profiles);
        given(authService.getCurrentUser()).willReturn(UserModel.builder().username("username").build());
        given(relationRepository.findAllRelationByUsernameWhereUserListAreSenderOrReceiver(anyString(), anyList())).willReturn(of());
        profiles.forEach(p -> given(profileMapper.toDtoLight(p)).willReturn(ProfileDto.builder().username(p.getUser().getUsername()).build()));

        // when
        final Page<ProfileDto> result = profileService.getAllProfiles(Pageable.unpaged());

        // then
        final int size = profiles.getSize();
        assertThat(result).hasSize(size);
        verify(profileRepository, times(1)).findAll(any(Pageable.class));
        verify(profileMapper, times(size)).toDtoLight(any());
    }

    @Nested
    @DisplayName("# uploadAvatar")
    class UploadAvatarTest {
        @Test
        @DisplayName("doit retourner un dtoLight avec l'imageUrl quand l'utilisateur existe")
        void should_return_profile_when_username_exists() {
            // given
            final MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
            final ProfileModel profileModel = generateRandomProfile();
            final String username = profileModel.getUser().getUsername();
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(username)).willReturn(Optional.of(profileModel));
            final String imageUrl = "url_du_fichier";
            given(cloudService.uploadPublicImage(file)).willReturn(imageUrl);

            // when
            profileService.uploadAvatar(file, username);

            // then
            verify(cloudService, times(1)).uploadPublicImage(file);
            verify(profileRepository, times(1)).save(profileCaptor.capture());
            final ProfileModel actualProfile = profileCaptor.getValue();
            assertThat(actualProfile.getImageUrl()).isEqualTo(imageUrl);
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur n'existe pas")
        void should_throw_exception_when_user_does_not_exists() {
            // given
            final MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
            final String username = "username";
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(username)).willReturn(Optional.empty());

            // when - then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.uploadAvatar(file, username));
            assertImplicactionException(e, EntityNotFoundException.class, PROFILE_NOT_FOUND, username);
        }
    }
}
