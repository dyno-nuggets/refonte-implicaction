package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RelationRepository relationRepository;
    @Mock
    private AuthService authService;
    @Mock
    private UserAdapter userAdapter;

    private UserService userService;

    @BeforeEach
    void setMockOutput() {
        userService = new UserService(
                userRepository,
                relationRepository,
                authService,
                userAdapter
        );
    }

    @Test
    @DisplayName("Should Retrieve User by Id")
    void getUserByIdWithExistingUserId() {
        User user = User.builder().id(123L).build();
        UserDto expectedUser = UserDto.builder().id(123L).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userAdapter.toDto(user)).thenReturn(expectedUser);

        UserDto actualUser = userService.getUserById(123L);

        assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
    }

    @Test
    @DisplayName("Should Throw Exception")
    void getUserByIdWithNonExistingUserId() {
        final long notFoundId = 10000L;
        final String expectedMessage = "No user found with id " + notFoundId;

        when(userRepository.findById(notFoundId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(notFoundId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(expectedMessage);
    }
}
