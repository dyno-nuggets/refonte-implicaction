package com.dynonuggets.refonteimplicaction.community.profile.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.error.TechnicalException;
import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.service.UserService;
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
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.community.profile.error.ProfileErrorResult.PROFILE_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_ALREADY_EXISTS_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static java.lang.String.format;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    AuthService authService;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileAdapter profileAdapter;
    @Mock
    CloudService cloudService;
    @Mock
    UserService userService;
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
            final UserModel userModel = generateRandomUser();
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
            final UserModel userModel = generateRandomUser();
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
            given(profileAdapter.toDto(any())).willReturn(expectedProfileDto);

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
            given(profileAdapter.toDto(any())).willReturn(expectedProfileDto);
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
            verify(profileAdapter, times(1)).toDto(any());
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
            verifyNoInteractions(profileAdapter);
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
            verifyNoInteractions(profileAdapter);
        }
    }

    @Nested
    @DisplayName("# updateAvatar")
    class UpdateAvatarTest {
        @Test
        @DisplayName("doit mettre à jour l'avatar de l'utilisateur quand updateAvatar est lancé avec un fichier valide et que l'utilisateur correspond à l'utilisateur courant")
        void should_update_avatar_when_updateAvatar_and_file_is_valid_and_current_user_is_user_to_update() {
            // given
            final UserModel currentUser = UserModel.builder().username("currentUser").build();
            final String username = "currentUser";
            final MockMultipartFile mockMultipartFile = new MockMultipartFile("avatar", "avatar.png", IMAGE_PNG_VALUE, "je suis un png".getBytes());
            final ProfileModel profile = ProfileModel.builder().user(currentUser).build();
            final FileModel fileModel = FileModel.builder().build();
            profile.setAvatar(fileModel);
            willDoNothing().given(authService).verifyAccessIsGranted(any());
            given(profileRepository.findByUser_UsernameAndUser_EnabledTrue(username)).willReturn(Optional.of(profile));
            given(cloudService.uploadImage(any())).willReturn(fileModel);
            given(profileRepository.save(any())).willReturn(profile);
            given(profileAdapter.toDto(any())).willReturn(ProfileDto.builder().username(username).avatar("avatar.png").build());

            // when
            final ProfileDto profileDto = profileService.updateAvatar(mockMultipartFile, username);

            // then
            assertThat(profileDto)
                    .isNotNull()
                    .extracting(ProfileDto::getAvatar)
                    .isEqualTo("avatar.png");
            verify(authService, times(1)).verifyAccessIsGranted(any());
            verify(profileRepository, times(1)).findByUser_UsernameAndUser_EnabledTrue(any());
            verify(cloudService, times(1)).uploadImage(any());
            verify(profileRepository, times(1)).save(any());
            verify(profileAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("doit retourner une exception lorsque updateAvatar est lancé pour un autre utilisateur que l'utilisateur courant")
        void should_throw_exception_when_updateAvatar_with_currentUser_is_not_user_updated() {
            // given
            final MockMultipartFile mockMultipartFile = new MockMultipartFile("avatar", "avatar.png", IMAGE_PNG_VALUE, "je suis un png".getBytes());
            final String username = "notCurrentUser";
            willThrow(new CoreException(OPERATION_NOT_PERMITTED)).given(authService).verifyAccessIsGranted(username);

            // then
            final ImplicactionException e = assertThrows(ImplicactionException.class, () -> profileService.updateAvatar(mockMultipartFile, username));
            assertImplicactionException(e, CoreException.class, OPERATION_NOT_PERMITTED);
            verifyNoInteractions(cloudService);
            verifyNoInteractions(profileRepository);
            verifyNoInteractions(profileAdapter);
        }
    }

    @Test
    @DisplayName("doit retourner la liste des profils des utilisateurs")
    void should_get_all_profiles_when_getAllProfiles() {
        final Page<ProfileModel> profiles = new PageImpl<>(of(generateRandomProfile(), generateRandomProfile(), generateRandomProfile()));
        given(profileRepository.findAll(any(Pageable.class))).willReturn(profiles);

        // when
        final Page<ProfileDto> result = profileService.getAllProfiles(Pageable.unpaged());

        // then
        final int size = profiles.getSize();
        assertThat(result).hasSize(size);
        verify(profileRepository, times(1)).findAll(any(Pageable.class));
        verify(profileAdapter, times(size)).toDto(any());
    }
}
