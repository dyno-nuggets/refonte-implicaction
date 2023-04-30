package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.event.UserEnabledEvent;
import com.dynonuggets.refonteimplicaction.core.mapper.UserMapper;
import com.dynonuggets.refonteimplicaction.core.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum.*;
import static com.dynonuggets.refonteimplicaction.core.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.error.UserErrorResult.USER_ID_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.utils.UserTestUtils.generateRandomUserDto;
import static com.dynonuggets.refonteimplicaction.core.utils.UserTestUtils.generateRandomUserModel;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    List<UserModel> mockedUsers;
    UserModel mockedUser;
    UserDto mockedUserDto;

    @Mock
    ApplicationEventPublisher publisher;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    RoleService roleService;
    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<UserModel> argumentCaptor;

    @BeforeEach
    void setMockOutput() {
        mockedUsers = List.of(
                generateRandomUserModel(Set.of(ROLE_USER), true),
                generateRandomUserModel(Set.of(ROLE_USER, ROLE_PREMIUM), true),
                generateRandomUserModel(Set.of(ROLE_ADMIN), true),
                generateRandomUserModel(Set.of(ROLE_USER, ROLE_ADMIN), true)
        );
        mockedUser = UserTestUtils.generateRandomUserModel();
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
        verify(userMapper, times(nbUsers)).toDto(any());
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
            given(userMapper.toDto(any())).willReturn(userDto);

            // then
            assertThat(userService.getUserById(expectedId)).hasFieldOrPropertyWithValue("id", expectedId);
            verify(userRepository, times(1)).findById(anyLong());
            verify(userMapper, times(1)).toDto(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur dont l'id est fourni n'existe pas")
        void should_throw_UserNotFoundException_when_getUserById_and_user_not_exists() {
            // given
            final long userId = mockedUser.getId();
            given(userRepository.findById(userId)).willReturn(empty());

            // when / then
            final ImplicactionException exception = assertThrows(ImplicactionException.class, () -> userService.getUserById(userId));
            assertImplicactionException(exception, EntityNotFoundException.class, USER_ID_NOT_FOUND, Long.toString(userId));
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
            final ImplicactionException exception = assertThrows(ImplicactionException.class, () -> userService.getUserByIdIfExists(userId));
            assertImplicactionException(exception, EntityNotFoundException.class, USER_ID_NOT_FOUND, Long.toString(userId));
        }
    }

    @Nested
    @DisplayName("# getUserByUsernameIfExists")
    class getUserByUsernameIfExistsTest {
        @Test
        @DisplayName("doit retourner un utilisateur quand le username transmis existe")
        void should_return_user_when_getUserByUsernameIfExists_and_username_exists() {
            // given
            final String username = mockedUser.getUsername();
            given(userRepository.findByUsername(username)).willReturn(Optional.of(mockedUser));

            // then
            assertThat(userService.getUserByUsernameIfExists(username))
                    .usingRecursiveComparison()
                    .isEqualTo(mockedUser);
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur n'existe pas")
        void should_throw_exception_when_getUserByIdIfExists_and_userId_not_exists() {
            // given
            final String username = mockedUser.getUsername();
            given(userRepository.findByUsername(username)).willReturn(empty());

            // when / then
            final ImplicactionException exception = assertThrows(ImplicactionException.class, () -> userService.getUserByUsernameIfExists(username));
            assertImplicactionException(exception, EntityNotFoundException.class, USERNAME_NOT_FOUND, username);
        }
    }

    @Nested
    @DisplayName("# enableUser")
    class EnableUserTests {
        @Test
        @DisplayName("doit activer l'utilisateur et un évènement doit être publié")
        void should_enable_user_and_send_envent() {
            // given
            final UserModel expectedUser = generateRandomUserModel(Set.of(ROLE_USER), false);
            final String username = expectedUser.getUsername();
            final RoleModel roleUser = RoleModel.builder().name(ROLE_USER).build();
            given(userRepository.findByUsername(username)).willReturn(Optional.of(expectedUser));
            given(userRepository.save(expectedUser)).willReturn(expectedUser);
            given(roleService.getRoleByName(ROLE_USER)).willReturn(roleUser);

            // when
            userService.enableUser(username);

            // then
            verify(userRepository, times(1)).save(argumentCaptor.capture());
            verify(publisher, times(1)).publishEvent(any(UserEnabledEvent.class));
            final UserModel actualUser = argumentCaptor.getValue();
            assertThat(actualUser.isEnabled()).isTrue();
            assertThat(actualUser.getRoles()).contains(roleUser);
        }

        @Test
        @DisplayName("doit lancer une exception quand l'utilisateur n'existe pas et aucun évènement ne doit être publié")
        void should_throw_exception_and_no_event_should_be_published() {
            // given
            final UserModel expectedUser = generateRandomUserModel(Set.of(ROLE_USER), false);
            final String username = expectedUser.getUsername();
            given(userRepository.findByUsername(username)).willReturn(Optional.empty());

            // when
            final ImplicactionException exception = assertThrows(ImplicactionException.class, () -> userService.getUserByUsernameIfExists(username));
            assertImplicactionException(exception, EntityNotFoundException.class, USERNAME_NOT_FOUND, username);
            verify(userRepository, times(1)).findByUsername(anyString());
            verifyNoMoreInteractions(userRepository);
            verifyNoInteractions(publisher);
        }
    }

    @Nested
    @DisplayName("# updateUserRoles")
    class UpdateUserRolesTests {
        @Test
        @DisplayName("doit mettre à jour les rôles de l'utilisateur quand l'utilisateur existe")
        void should_update_user_roles_when_user_exists() {
            // given
            final Set<RoleEnum> roleEnums = Set.of(ROLE_USER, ROLE_PREMIUM);
            final Set<RoleModel> roles = roleEnums.stream().map(name -> RoleModel.builder().name(name).build()).collect(Collectors.toSet());
            final UserModel expectedUser = UserTestUtils.generateRandomUserModel(Set.of(ROLE_USER), true);
            given(userRepository.findByUsername(expectedUser.getUsername())).willReturn(of(expectedUser));
            given(userRepository.save(expectedUser)).willReturn(expectedUser);
            roles.forEach(role -> given(roleService.getRoleByName(role.getName())).willReturn(role));

            // when
            userService.updateUserRoles(expectedUser.getUsername(), roleEnums);

            // then
            verify(userMapper, times(1)).toDto(expectedUser);
            verify(userRepository, times(1)).save(argumentCaptor.capture());
            final UserModel actualUser = argumentCaptor.getValue();
            assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
            assertThat(actualUser.getRoles()).containsAll(roles);
        }
    }
}
