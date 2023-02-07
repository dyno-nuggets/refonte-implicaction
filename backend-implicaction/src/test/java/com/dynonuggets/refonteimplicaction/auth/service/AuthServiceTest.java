package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.*;
import com.dynonuggets.refonteimplicaction.auth.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import com.dynonuggets.refonteimplicaction.service.RefreshTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.*;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.core.util.AssertionUtils.assertImplicactionException;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    RefreshTokenService refreshTokenService;
    @Mock
    UserAdapter userAdapter;
    @Mock
    RoleRepository roleRepository;
    @Mock
    NotificationRepository notificationRepository;
    @InjectMocks
    AuthService authService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptorCaptor;

    RegisterRequest generateValidRegisterRequest() {
        return RegisterRequest.builder()
                .username(randomAlphabetic(10))
                .email(format("%s@mail.com", randomAlphabetic(10)))
                .password(randomAlphabetic(10))
                .firstname(randomAlphabetic(10))
                .lastname(randomAlphabetic(10)).build();
    }

    @Nested
    @DisplayName("# signup")
    class SignupTest {
        @Test
        @DisplayName("doit créer un nouvel utilisateur quand l'utilisateur n'existe pas déjà")
        void should_register_user_when_request_is_valid_and_user_not_already_exists() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            final String expectedPassword = "encriptedPassword";
            given(userRepository.existsByUsername(any())).willReturn(false);
            given(userRepository.existsByEmail(any())).willReturn(false);
            given(passwordEncoder.encode(any())).willReturn(expectedPassword);

            // when
            authService.signup(validLoginRequest);

            // then
            verify(userRepository, times(1)).save(userArgumentCaptorCaptor.capture());
            final User savedUser = userArgumentCaptorCaptor.getValue();
            assertThat(savedUser)
                    .usingRecursiveComparison()
                    .ignoringFields("password", "birthday", "image", "purpose", "activatedAt", "roles", "registeredAt", "trainings", "active", "groups", "expectation", "activationKey", "experiences", "url", "presentation", "contribution", "phoneNumber", "hobbies", "id", "notifications")
                    .isEqualTo(validLoginRequest);
            assertThat(savedUser.getPassword()).isEqualTo(expectedPassword);
            assertThat(savedUser.getActivationKey()).isNotNull();
            verify(userRepository, times(1)).existsByUsername(any());
            verify(userRepository, times(1)).existsByEmail(any());
            verify(passwordEncoder, times(1)).encode(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand le nom d'utilisateur n'est pas disponible")
        void should_throw_exception_when_username_is_not_available() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            given(userRepository.existsByUsername(any())).willReturn(true);

            // when
            final ImplicactionException actualException =
                    assertThrows(AuthenticationException.class, () -> authService.signup(validLoginRequest));

            // then
            assertThat(actualException)
                    .extracting(ImplicactionException::getErrorResult)
                    .isInstanceOf(AuthErrorResult.class)
                    .isExactlyInstanceOf(AuthErrorResult.class)
                    .isSameAs(USERNAME_ALREADY_EXISTS);
            assertThat(actualException.getMessage())
                    .isEqualTo(format(USERNAME_ALREADY_EXISTS.getMessage(), validLoginRequest.getUsername()));
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand l'email' n'est pas disponible")
        void should_throw_exception_when_email_is_not_available() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            given(userRepository.existsByEmail(any())).willReturn(true);

            // when
            final ImplicactionException actualException =
                    assertThrows(ImplicactionException.class, () -> authService.signup(validLoginRequest));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, EMAIL_ALREADY_EXISTS, validLoginRequest.getEmail());
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("# verifyAccount")
    class VerifyAccountTest {
        @Test
        @DisplayName("doit activer l'utilisateur correspondant à la clé d'activation transmise s'il n'est pas déjà activé")
        void should_activate_corresponding_user() {
            // given
            final User user = generateRandomUser(List.of(RoleEnum.USER), false);
            given(userRepository.findByActivationKey(any())).willReturn(Optional.of(user));
            final Instant activatedAt = now();

            // when
            authService.verifyAccount("activationKey");

            // then
            verify(userRepository, times(1)).save(userArgumentCaptorCaptor.capture());
            final User savedUser = userArgumentCaptorCaptor.getValue();
            assertThat(savedUser)
                    // les champs activatedAt et active doivent être différents donc on les ignore
                    .usingRecursiveComparison().ignoringFields("activatedAt", "active")
                    .isEqualTo(user);
            assertThat(savedUser.getActivatedAt()).isAfterOrEqualTo(activatedAt);
            assertThat(savedUser.isActive()).isTrue();
            verify(userRepository, times(1)).findByActivationKey(any());
        }

        @Test
        @DisplayName("doit lancer une exception si aucun utilisateur ne correspond à la clé d'activation fournie")
        void should_throw_exception_when_no_user_matching_activation_key() {
            // given
            final String activationKey = "activationKey";
            given(userRepository.findByActivationKey(any())).willReturn(Optional.empty());

            // when
            final ImplicactionException actualException =
                    assertThrows(AuthenticationException.class, () -> authService.verifyAccount(activationKey));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, ACTIVATION_KEY_NOT_FOUND, activationKey);
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("doit lancer une exception si l'utilisateur associé à la clé est déjà actif")
        void should_throw_exception_when_user_is_already_activated() {
            // given
            final String activationKey = "activationKey";
            final User user = User.builder().activatedAt(now()).build();
            given(userRepository.findByActivationKey(any())).willReturn(Optional.ofNullable(user));

            // when
            final ImplicactionException actualException =
                    assertThrows(AuthenticationException.class, () -> authService.verifyAccount(activationKey));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, USER_ALREADY_ACTIVATED, activationKey);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("# login")
    class LoginTest {
        @Test
        @DisplayName("doit identifier l'utilisateur quand il existe")
        void should_log_user_when_exists() {
            // given
            final User user = generateRandomUser();
            final String username = user.getUsername();
            final LoginRequest loginRequest = LoginRequest.builder().username(username).password("password").build();
            final String jwtToken = "jwt-token";
            final String refreshToken = "refreshToken";
            final Instant expiresAt = now();
            final UserDto expectedUserDto = UserDto.builder().username(username).build();

            given(authenticationManager.authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            given(jwtProvider.generateToken(any())).willReturn(jwtToken);
            given(refreshTokenService.generateRefreshToken()).willReturn(RefreshTokenDto.builder().token(refreshToken).build());
            given(userRepository.findByUsername(any())).willReturn(Optional.of(user));
            given(userAdapter.toDtoLight(any())).willReturn(expectedUserDto);

            // when
            final LoginResponse actualResponse = authService.login(loginRequest);

            // then
            assertThat(actualResponse.getAuthenticationToken()).isEqualTo(jwtToken);
            assertThat(actualResponse.getRefreshToken()).isEqualTo(refreshToken);
            assertThat(actualResponse.getExpiresAt()).isBetween(expiresAt, now());
            assertThat(actualResponse.getCurrentUser())
                    .isNotNull()
                    .extracting(UserDto::getUsername).isEqualTo(username);

            verify(authenticationManager, times(1)).authenticate(any());
            verify(jwtProvider, times(1)).generateToken(any());
            verify(refreshTokenService, times(1)).generateRefreshToken();
            verify(userRepository, times(1)).findByUsername(any());
            verify(userAdapter, times(1)).toDtoLight(any());
        }

        // TODO: chercher comment faire les tests en cas d'échec
    }
}