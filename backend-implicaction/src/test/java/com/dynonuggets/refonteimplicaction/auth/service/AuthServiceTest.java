package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.dto.*;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.mapper.EmailValidationNotificationMapper;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.service.RoleService;
import com.dynonuggets.refonteimplicaction.notification.service.NotificationService;
import com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.mapper.UserMapper;
import com.dynonuggets.refonteimplicaction.user.service.UserService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.*;
import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.Optional.of;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService;
    @Mock
    UserMapper userMapper;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    RefreshTokenService refreshTokenService;
    @Mock
    RoleService roleService;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    NotificationService notificationService;
    @Mock
    EmailValidationNotificationMapper emailValidationNotificationMapper;
    @InjectMocks
    AuthService authService;
    @Captor
    private ArgumentCaptor<UserModel> userArgumentCaptorCaptor;
    @Captor
    private ArgumentCaptor<ProfileModel> profileArgumentCaptorCaptor;

    RegisterRequest generateValidRegisterRequest() {
        return RegisterRequest.builder()
                .username(randomAlphabetic(10))
                .email(format("%s@mail.com", randomAlphabetic(10)))
                .password(randomAlphabetic(10))
                .firstname(randomAlphabetic(10))
                .lastname(randomAlphabetic(10)).build();
    }

    Authentication authentication;
    SecurityContext securityContext;


    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Nested
    @DisplayName("# signup")
    class SignupTest {
        @Test
        @DisplayName("doit créer un nouvel utilisateur quand l'utilisateur n'existe pas déjà")
        void should_register_user_when_request_is_valid_and_user_not_already_exists() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            final String expectedPassword = "encryptedPassword";
            given(userRepository.existsByUsername(any())).willReturn(false);
            given(userRepository.existsByEmail(any())).willReturn(false);
            given(passwordEncoder.encode(any())).willReturn(expectedPassword);
            willDoNothing().given(notificationService).notify(any(UserModel.class), any(EmailValidationNotificationMapper.class));
            given(userRepository.save(any())).willReturn(UserModel.builder().build());
            given(roleService.getRoleByName(anyString())).willReturn(Role.builder().name(RoleEnum.USER.getLongName()).build());

            // when
            authService.signup(validLoginRequest);

            // then
            verify(userRepository, times(1)).save(userArgumentCaptorCaptor.capture());
            final UserModel savedUser = userArgumentCaptorCaptor.getValue();
            assertThat(savedUser)
                    .usingRecursiveComparison()
                    .ignoringFields("password", "birthday", "image", "purpose", "activatedAt", "roles", "registeredAt", "trainings", "enabled", "emailVerified", "groups", "expectation", "activationKey", "experiences", "url", "presentation", "contribution", "phoneNumber", "hobbies", "id", "notifications")
                    .isEqualTo(validLoginRequest);
            assertThat(savedUser.getPassword()).isEqualTo(expectedPassword);
            assertThat(savedUser.getActivationKey()).isNotNull();
            assertThat(savedUser)
                    .extracting(UserModel::isEnabled, UserModel::isEmailVerified)
                    .allMatch(value -> isFalse((Boolean) value));
            verify(userRepository, times(1)).existsByUsername(any());
            verify(userRepository, times(1)).existsByEmail(any());
            verify(passwordEncoder, times(1)).encode(any());
            verify(userRepository, times(1)).save(any());
            verify(notificationService, times(1)).notify(any(UserModel.class), any(EmailValidationNotificationMapper.class));
        }

        @Test
        @DisplayName("doit lancer une exception quand le nom d'utilisateur n'est pas disponible")
        void should_throw_exception_when_username_is_not_available() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            given(userRepository.existsByUsername(any())).willReturn(true);

            // when
            final ImplicactionException actualException = assertThrows(AuthenticationException.class, () -> authService.signup(validLoginRequest));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, USERNAME_ALREADY_EXISTS, validLoginRequest.getUsername());
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("doit lancer une exception quand l'email' n'est pas disponible")
        void should_throw_exception_when_email_is_not_available() {
            // given
            final RegisterRequest validLoginRequest = generateValidRegisterRequest();
            given(userRepository.existsByEmail(any())).willReturn(true);

            // when
            final ImplicactionException actualException = assertThrows(AuthenticationException.class, () -> authService.signup(validLoginRequest));

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
            final UserModel user = generateRandomUser(List.of(RoleEnum.USER), false);
            given(userRepository.findByActivationKey(any())).willReturn(of(user));

            // when
            authService.verifyAccount("activationKey");

            // then
            verify(userRepository, times(1)).save(userArgumentCaptorCaptor.capture());
            final UserModel savedUser = userArgumentCaptorCaptor.getValue();
            assertThat(savedUser)
                    // les champs activatedAt et active doivent être différents donc, on les ignore
                    .usingRecursiveComparison().ignoringFields("emailVerified")
                    .isEqualTo(user);
            assertThat(savedUser.isEmailVerified()).isTrue();
            verify(userRepository, times(1)).findByActivationKey(any());
        }

        @Test
        @DisplayName("doit lancer une exception si aucun utilisateur ne correspond à la clé d'activation fournie")
        void should_throw_exception_when_no_user_matching_activation_key() {
            // given
            final String activationKey = "activationKey";
            given(userRepository.findByActivationKey(any())).willReturn(Optional.empty());

            // when
            final ImplicactionException actualException = assertThrows(EntityNotFoundException.class, () -> authService.verifyAccount(activationKey));

            // then
            assertImplicactionException(actualException, EntityNotFoundException.class, ACTIVATION_KEY_NOT_FOUND, activationKey);
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("doit lancer une exception si l'utilisateur associé à la clé est déjà actif")
        void should_throw_exception_when_user_is_already_activated() {
            // given
            final String activationKey = "activationKey";
            final UserModel user = UserModel.builder().username("username").emailVerified(true).build();
            given(userRepository.findByActivationKey(any())).willReturn(of(user));

            // when
            final ImplicactionException actualException =
                    assertThrows(AuthenticationException.class, () -> authService.verifyAccount(activationKey));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, USER_MAIL_IS_ALREADY_VERIFIED, user.getUsername());
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
            final UserModel user = generateRandomUser();
            final String username = user.getUsername();
            final LoginRequest loginRequest = LoginRequest.builder().username(username).password("password").build();
            final String jwtToken = "jwt-token";
            final String refreshToken = "refreshToken";
            final Instant expiresAt = now();
            final UserDto expectedUserDto = UserDto.builder().username(username).build();

            given(authenticationManager.authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            given(jwtProvider.generateToken(any())).willReturn(jwtToken);
            given(refreshTokenService.generateRefreshToken()).willReturn(RefreshTokenDto.builder().token(refreshToken).build());
            given(userRepository.findByUsername(any())).willReturn(of(user));
            given(userMapper.toDtoLight(any())).willReturn(expectedUserDto);

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
            verify(userMapper, times(1)).toDtoLight(any());
        }

        // TODO: chercher comment faire les tests en cas d'échec
    }

    @Nested
    @DisplayName("# getCurrentUser")
    class GetCurrentUserClass {
        @Test
        @DisplayName("doit retourner l'utilisateur courant")
        void test() {
            // given
            final String username = "username";
            final String password = "password";
            final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, password, List.of());
            given(securityContext.getAuthentication()).willReturn(authentication);
            given(securityContext.getAuthentication().getPrincipal()).willReturn(user);
            given(userService.getUserByUsernameIfExists(anyString())).willReturn(UserModel.builder().username("username").build());

            // when
            final UserModel currentUser = authService.getCurrentUser();

            // then
            assertThat(currentUser)
                    .isNotNull()
                    .extracting(UserModel::getUsername)
                    .isEqualTo(username);
        }
    }

    @Nested
    @DisplayName("# refreshToken")
    class RefreshToken {
        @Test
        @DisplayName("doit retourner un LoginResponse si l'utilisateur existe et le refresh token est valide")
        void should_return_LoginResponse_when_token_is_valid() {
            // given
            final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().username("username").refreshToken("refreshToken").build();
            final String username = refreshTokenRequest.getUsername();
            final UserModel user = UserModel.builder().username(username).build();
            final LoginResponse expectedResponse = LoginResponse.builder().refreshToken("refreshToken").authenticationToken("jwtToken").expiresAt(now().plusMillis(jwtProvider.getJwtExpirationInMillis())).build();
            given(userService.getUserByUsernameIfExists(username)).willReturn(user);
            willDoNothing().given(refreshTokenService).validateRefreshToken(anyString());
            given(jwtProvider.generateTokenWithUsername(username)).willReturn("jwtToken");

            // when
            final LoginResponse loginResponse = authService.refreshToken(refreshTokenRequest);

            // then
            assertThat(loginResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("expiresAt", "currentUser")
                    .isEqualTo(expectedResponse);

            assertThat(loginResponse.getExpiresAt())
                    .isBetween(expectedResponse.getExpiresAt(), now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
            verify(userService, times(1)).getUserByUsernameIfExists(username);
            verify(refreshTokenService, times(1)).validateRefreshToken(anyString());
            verify(jwtProvider, times(1)).generateTokenWithUsername(username);
        }

        @Test
        @DisplayName("doit lancer une exception si le nom d'utilisateur contenu dans la requête n'existe pas")
        void should_throw_exception_when_username_requests_not_exists() {
            // given
            final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().username("username").refreshToken("refreshToken").build();
            final String username = refreshTokenRequest.getUsername();
            given(userService.getUserByUsernameIfExists(username)).willThrow(new EntityNotFoundException(USERNAME_NOT_FOUND, username));

            // when
            final ImplicactionException actualException = assertThrows(EntityNotFoundException.class, () -> authService.refreshToken(refreshTokenRequest));

            // then
            assertImplicactionException(actualException, EntityNotFoundException.class, USERNAME_NOT_FOUND, username);
            verifyNoInteractions(refreshTokenService);
            verifyNoInteractions(jwtProvider);
        }

        @Test
        @DisplayName("doit lancer une exception si le refresh token n'existe pas")
        void should_throw_exception_when_refresh_token_not_exists() {
            // given
            final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().username("username").refreshToken("refreshToken").build();
            final String username = refreshTokenRequest.getUsername();
            final UserModel user = UserModel.builder().username(username).build();
            given(userService.getUserByUsernameIfExists(username)).willReturn(user);
            willThrow(new AuthenticationException(REFRESH_TOKEN_EXPIRED)).given(refreshTokenService).validateRefreshToken(anyString());

            // when
            final ImplicactionException actualException = assertThrows(AuthenticationException.class, () -> authService.refreshToken(refreshTokenRequest));

            // then
            assertImplicactionException(actualException, AuthenticationException.class, REFRESH_TOKEN_EXPIRED);
            verify(refreshTokenService, times(1)).validateRefreshToken(anyString());
            verifyNoInteractions(jwtProvider);
        }
    }
}
