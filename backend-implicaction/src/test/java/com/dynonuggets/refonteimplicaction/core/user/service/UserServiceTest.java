package com.dynonuggets.refonteimplicaction.core.user.service;

import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.user.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.core.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.core.user.dto.UserDto;
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

import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.auth.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserTestUtils.generateRandomUserDto;
import static com.dynonuggets.refonteimplicaction.core.user.domain.enums.RoleEnum.*;
import static com.dynonuggets.refonteimplicaction.core.user.error.UserErrorResult.USER_ID_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.util.AssertionUtils.assertImplicactionException;
import static java.util.List.of;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    List<User> mockedUsers;
    User mockedUser;
    UserDto mockedUserDto;

    @Mock
    UserRepository userRepository;
    @Mock
    UserAdapter userAdapter;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setMockOutput() {
        mockedUsers = List.of(
                generateRandomUser(of(USER), true),
                generateRandomUser(of(USER, PREMIUM), true),
                generateRandomUser(of(ADMIN), true),
                generateRandomUser(of(USER, ADMIN), true)
        );
        mockedUser = generateRandomUser();
        mockedUserDto = generateRandomUserDto();
    }

    @Test
    @DisplayName("doit retourner la liste des utilisateurs")
    void should_get_all_users_when_getAll() {
        final int nbUsers = mockedUsers.size();
        given(userRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(mockedUsers));

        // when
        final Page<UserDto> result = userService.getAll(Pageable.unpaged());

        // then
        assertThat(result).hasSize(nbUsers);
        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verify(userAdapter, times(nbUsers)).toDto(any());
    }

    @Nested
    @DisplayName("# getUserById")
    class GetUserByIdTest {
        @Test
        @DisplayName("doit retourner l'utilisateur correspondant à l'id en paramètres quand il existe")
        void should_get_user_when_getUserById_and_user_exists() {
            // given
            final Long expectedId = mockedUser.getId();
            final UserDto userDto = UserDto.builder().id(expectedId).build();
            given(userRepository.findById(expectedId)).willReturn(Optional.of(mockedUser));
            given(userAdapter.toDto(any())).willReturn(userDto);

            // then
            assertThat(userService.getUserById(expectedId)).hasFieldOrPropertyWithValue("id", expectedId);
            verify(userRepository, times(1)).findById(anyLong());
            verify(userAdapter, times(1)).toDto(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur dont l'id est fourni n'existe pas")
        void should_throw_UserNotFoundException_when_getUserById_and_user_not_exists() {
            // given
            final long userId = mockedUser.getId();
            given(userRepository.findById(userId)).willReturn(empty());

            // when / then
            final ImplicactionException exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
            assertImplicactionException(exception, EntityNotFoundException.class, USER_ID_NOT_FOUND, Long.toString(userId));
        }
    }

    @Nested
    @DisplayName("# updateUser")
    class UpdateUserTest {
        @Test
        @DisplayName("doit mettre à jour les champs de l'utilisateur fourni en paramètres quand celui-ci existe")
        void should_get_user_updated_when_updateUser_and_user_exists() {
            // given
            final UserDto expectedDto = UserDto.builder().id(mockedUser.getId()).build();
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
        @DisplayName("doit lancer une exception lorsque l'on essaye de mettre à jour un utilisateur qui n'existe pas")
        void should_throw_UserNotFoundException_when_updateUser_and_user_not_exists() {
            // given
            given(userRepository.findById(any())).willReturn(empty());

            // when / then
            final ImplicactionException exception = assertThrows(EntityNotFoundException.class, () -> userService.updateUser(mockedUserDto));
            assertImplicactionException(exception, EntityNotFoundException.class, USER_ID_NOT_FOUND, Long.toString(mockedUserDto.getId()));
            verify(userRepository, times(1)).findById(any());
            verify(userRepository, times(0)).save(any());
            verify(userAdapter, times(0)).toDto(any());
        }
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
        void should_throw_exception_when_getUserByIdIfExists_and_userId_not_exists() {
            // given
            final Long userId = mockedUser.getId();
            given(userRepository.findById(userId)).willReturn(empty());

            // when / then
            final ImplicactionException exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
            assertImplicactionException(exception, EntityNotFoundException.class, USER_ID_NOT_FOUND, Long.toString(userId));
        }
    }
}
