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
    List<User> users;
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
        users = of(
                generateRandomUser(of(USER)),
                generateRandomUser(of(USER, PREMIUM)),
                generateRandomUser(of(ADMIN)),
                generateRandomUser(of(USER, ADMIN))
        );
    }

    @Test
    void should_update_image_profile() {
        // given
        User currentUser = User.builder().id(123L).build();
        FileModel fileModel = FileModel.builder().objectKey("blabla").build();
        FileModel fileModel2 = FileModel.builder().id(4L).objectKey(fileModel.getObjectKey()).build();
        User currentUserAfterImageUpdate = User.builder().id(123L).image(fileModel2).build();
        UserDto userDto = UserDto.builder().id(123L).imageUrl("http://file.url/" + fileModel2.getObjectKey()).build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.png",
                IMAGE_PNG_VALUE,
                "test data".getBytes()
        );
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(cloudService.uploadImage(any())).willReturn(fileModel);
        given(fileRepository.save(any())).willReturn(fileModel2);
        given(userRepository.save(any())).willReturn(currentUserAfterImageUpdate);
        given(userAdapter.toDto(any(User.class))).willReturn(userDto);

        // when
        final UserDto actualUser = userService.updateImageProfile(mockMultipartFile);

        // then
        assertThat(actualUser.getImageUrl()).isEqualTo(userDto.getImageUrl());
    }

    @Test
    void should_get_all_users() {
        final int nbUsers = users.size();
        given(userRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(users));

        // when
        final Page<UserDto> result = userService.getAll(pageable);

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(nbUsers);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verify(userAdapter, times(nbUsers)).toDto(any());
    }

    @Nested
    @DisplayName("UserService#getUserById")
    class GetUserByIdTest {
        @Test
        @DisplayName("Doit retourner l'utilisateur correspondant à l'id en paramètres quand il existe")
        void should_get_user_when_getUserById_and_user_exists() {
            // given
            final User user = User.builder().id(12L).build();
            final UserDto userDto = UserDto.builder().id(user.getId()).build();
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            given(userAdapter.toDto(any())).willReturn(userDto);

            // then
            assertThat(userService.getUserById(user.getId()))
                    .isNotNull()
                    .isInstanceOf(UserDto.class)
                    .extracting(UserDto::getId)
                    .isEqualTo(user.getId());

            verify(userRepository, times(1)).findById(anyLong());
            verify(userAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("Doit lancer une exception quand l'utilisateur dont l'id est fourni n'existe pas")
        void should_throw_UserNotFoundException_when_getUserById_and_user_not_exists() {
            // given
            final long userId = 12L;
            final String message = format(USER_NOT_FOUND_MESSAGE, userId);
            given(userRepository.findById(anyLong())).willThrow(new UserNotFoundException(message));

            // when
            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

            // then
            assertThat(exception.getMessage())
                    .isEqualTo(message);
        }
    }

    @Nested
    @DisplayName("UserService#updateUser")
    class UpdateUserTest {
        @Test
        @DisplayName("Doit mettre à jour les champs de l'utilisateur fourni en paramètres quand celui-ci existe")
        void should_get_user_updated_when_updateUser_and_user_exists() {
            // given
            final User user = User.builder().id(12L).build();
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            given(userRepository.save(any())).willReturn(user);
            user.setFirstname("Han");
            user.setLastname("Solo");
            final UserDto dtoToUpdate = UserDto.builder().id(user.getId()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
            given(userAdapter.toDto(any())).willReturn(dtoToUpdate);

            // when
            UserDto resultDto = userService.updateUser(dtoToUpdate);

            // then
            assertThat(resultDto)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("firstname", "lastname")
                    .isEqualTo(dtoToUpdate);

            verify(userRepository, times(1)).findById(anyLong());
            verify(userRepository, times(1)).save(any());
            verify(userAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("Doit lancer une exception lorsque l'on essaye de mettre à jour un utilisateur qui n'existe pas")
        void should_throw_UserNotFoundException_when_updateUser_and_user_not_exists() {
            // given
            final UserDto userDto = UserDto.builder().id(12L).build();
            String message = format(USER_NOT_FOUND_MESSAGE, userDto.getId());
            given(userRepository.findById(anyLong()))
                    .willThrow(new UserNotFoundException(message));

            // when
            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));

            assertThat(exception.getMessage())
                    .isEqualTo(message);

            verify(userRepository, times(1)).findById(anyLong());
            verify(userRepository, times(0)).save(any());
            verify(userAdapter, times(0)).toDto(any());
        }
    }

    @Nested
    @DisplayName("UserService#getAllCommunity")
    class GetAllCommunityTest {
        @Test
        @DisplayName("L'accès à cette méthode doit être interdit si l'utilisateur n'est pas identifié et une exception doit-être lancée")
        void should_throw_UserNotFoundException_when_getAllCommunity_without_auth() {
            // given
            String message = format(USERNAME_NOT_FOUND_MESSAGE, "");
            given(authService.getCurrentUser())
                    .willThrow(new UserNotFoundException(message));

            final var exception =
                    assertThrows(UserNotFoundException.class, () -> userService.getAllCommunity(pageable));

            // then
            assertThat(exception.getMessage())
                    .isEqualTo(message);

            verifyNoInteractions(userRepository);
            verifyNoInteractions(userAdapter);
            verifyNoInteractions(relationRepository);
        }

        // TODO: rajouter les tests en rapport avec les relations
    }
}
