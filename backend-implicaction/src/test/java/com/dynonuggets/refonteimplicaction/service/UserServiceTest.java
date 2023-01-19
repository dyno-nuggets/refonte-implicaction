package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.model.RoleEnum.*;
import static com.dynonuggets.refonteimplicaction.utils.Message.USERNAME_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.USER_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUserDto;
import static java.lang.String.format;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    List<User> mockedUsers;
    User mockedUser;
    UserDto mockedUserDto;
    Pageable pageable = Pageable.unpaged();

    @Mock
    UserRepository userRepository;
    @Mock
    RelationRepository relationRepository;
    @Mock
    AuthService authService;
    @Mock
    UserAdapter userAdapter;
    @Mock
    S3CloudServiceImpl cloudService;
    @Mock
    FileRepository fileRepository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setMockOutput() {
        mockedUsers = of(
                generateRandomUser(of(USER), true),
                generateRandomUser(of(USER, PREMIUM), true),
                generateRandomUser(of(ADMIN), true),
                generateRandomUser(of(USER, ADMIN), true)
        );
        mockedUser = generateRandomUser();
        mockedUserDto = generateRandomUserDto();
    }

    @Test
    @DisplayName("doit retourner un UserDto avec le champ imageUrl mis à jour")
    void should_update_image_profile_when_updateImageProfile() {
        // given
        final UserDto expectedUserDto = UserDto.builder().imageUrl("imageUrl").build();
        final FileModel fileModel = FileModel.builder().objectKey("objectKey").build();
        final MockMultipartFile mockedFile = new MockMultipartFile(
                "user-file", "test.png", IMAGE_PNG_VALUE, "test data".getBytes()
        );

        given(authService.getCurrentUser()).willReturn(mockedUser);
        given(cloudService.uploadImage(mockedFile)).willReturn(fileModel);
        given(fileRepository.save(fileModel)).willReturn(fileModel);
        given(userRepository.save(mockedUser)).willReturn(mockedUser);
        given(userAdapter.toDto(mockedUser)).willReturn(expectedUserDto);

        // when
        final UserDto actualUserDto = userService.updateImageProfile(mockedFile);

        // then
        assertThat(actualUserDto)
                .hasFieldOrPropertyWithValue("id", expectedUserDto.getId())
                .hasFieldOrPropertyWithValue("imageUrl", expectedUserDto.getImageUrl());
    }

    @Test
    @DisplayName("doit retourner la liste des utilisateurs")
    void should_get_all_users_when_getAll() {
        final int nbUsers = mockedUsers.size();
        given(userRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(mockedUsers));

        // when
        final Page<UserDto> result = userService.getAll(pageable);

        // then
        assertThat(result)
                .hasSize(nbUsers);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verify(userAdapter, times(nbUsers)).toDto(any());
    }

    @Nested
    @DisplayName("# getUserById")
    class GetUserByIdTest {
        @Test
        @DisplayName("Doit retourner l'utilisateur correspondant à l'id en paramètres quand il existe")
        void should_get_user_when_getUserById_and_user_exists() {
            // given
            final Long expectedId = mockedUser.getId();
            final UserDto userDto = UserDto.builder().id(expectedId).build();

            given(userRepository.findById(expectedId)).willReturn(Optional.of(mockedUser));
            given(userAdapter.toDto(any())).willReturn(userDto);

            // then
            assertThat(userService.getUserById(expectedId))
                    .hasFieldOrPropertyWithValue("id", expectedId);

            verify(userRepository, times(1)).findById(anyLong());
            verify(userAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("Doit lancer une exception quand l'utilisateur dont l'id est fourni n'existe pas")
        void should_throw_UserNotFoundException_when_getUserById_and_user_not_exists() {
            // given
            final long userId = mockedUser.getId();
            final String expectedMessage = format(USER_NOT_FOUND_MESSAGE, userId);

            given(userRepository.findById(userId)).willThrow(new UserNotFoundException(expectedMessage));

            // when
            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

            // then
            assertThat(exception.getMessage())
                    .isEqualTo(expectedMessage);
        }
    }

    @Nested
    @DisplayName("# updateUser")
    class UpdateUserTest {
        @Test
        @DisplayName("Doit mettre à jour les champs de l'utilisateur fourni en paramètres quand celui-ci existe")
        void should_get_user_updated_when_updateUser_and_user_exists() {
            // given
            mockedUser.setFirstname("Han");
            mockedUser.setLastname("Solo");
            final UserDto expectedDto = UserDto.builder().id(mockedUser.getId()).firstname(mockedUser.getFirstname()).lastname(mockedUser.getLastname()).build();

            given(userRepository.findById(mockedUser.getId())).willReturn(Optional.of(mockedUser));
            given(userRepository.save(any())).willReturn(mockedUser);
            given(userAdapter.toDto(any())).willReturn(expectedDto);

            // when
            final UserDto actualDto = userService.updateUser(expectedDto);

            // then
            assertThat(actualDto)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("firstname", "lastname")
                    .isEqualTo(expectedDto);

            verify(userRepository, times(1)).findById(anyLong());
            verify(userRepository, times(1)).save(any());
            verify(userAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("Doit lancer une exception lorsque l'on essaye de mettre à jour un utilisateur qui n'existe pas")
        void should_throw_UserNotFoundException_when_updateUser_and_user_not_exists() {
            // given
            final String expectedMessage = format(USER_NOT_FOUND_MESSAGE, mockedUserDto.getId());

            given(userRepository.findById(mockedUserDto.getId())).willThrow(new UserNotFoundException(expectedMessage));

            // when
            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.updateUser(mockedUserDto));

            assertThat(exception.getMessage())
                    .isEqualTo(expectedMessage);

            verify(userRepository, times(1)).findById(anyLong());
            verify(userRepository, times(0)).save(any());
            verify(userAdapter, times(0)).toDto(any());
        }
    }

    @Nested
    @DisplayName("# getAllCommunity")
    class GetAllCommunityTest {
        @Test
        @DisplayName("L'accès à cette méthode doit être interdit si l'utilisateur n'est pas identifié et une exception doit-être lancée")
        void should_throw_UserNotFoundException_when_getAllCommunity_without_auth() {
            // given
            final String expectedMessage = format(USERNAME_NOT_FOUND_MESSAGE, "");
            given(authService.getCurrentUser())
                    .willThrow(new UserNotFoundException(expectedMessage));

            final var actualException =
                    assertThrows(UserNotFoundException.class, () -> userService.getAllCommunity(pageable));

            // then
            assertThat(actualException.getMessage())
                    .isEqualTo(expectedMessage);

            verifyNoInteractions(userRepository);
            verifyNoInteractions(userAdapter);
            verifyNoInteractions(relationRepository);
        }

        // TODO: rajouter les tests en rapport avec les relations
    }

    @Nested
    @DisplayName("# getUserByIdIfExists")
    class getUserByIdIfExistsTest {
        @Test
        @DisplayName("doit retourner un utilisateur quand l'id transmis existe")
        void should_return_user_when_getUserByIdIfExists_and_userId_exists() {
            // given
            final Long userId = mockedUser.getId();
            given(userRepository.findById(userId)).willReturn(Optional.of(mockedUser));

            // then
            assertThat(userService.getUserByIdIfExists(userId))
                    .usingRecursiveComparison()
                    .isEqualTo(mockedUser);
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur n'existe pas")
        void shoud_throw_exception_when_getUserByIdIfExists_and_userId_not_exists() {
            // given
            final Long userId = mockedUser.getId();
            final String expectedMessage = format(USER_NOT_FOUND_MESSAGE, userId);
            given(userRepository.findById(userId)).willReturn(Optional.empty());

            // when
            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

            // then
            assertThat(exception.getMessage())
                    .isEqualTo(expectedMessage);
        }
    }
}
