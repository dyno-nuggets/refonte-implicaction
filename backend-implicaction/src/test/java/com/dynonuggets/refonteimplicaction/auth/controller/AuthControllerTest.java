package com.dynonuggets.refonteimplicaction.auth.controller;

import com.dynonuggets.refonteimplicaction.auth.dto.LoginRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.LoginResponse;
import com.dynonuggets.refonteimplicaction.auth.dto.RefreshTokenRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.RegisterRequest;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.auth.service.RefreshTokenService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.REFRESH_TOKEN_DELETED_SUCCESSFULLY_MESSAGE;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.USER_SIGNUP_SUCCESS_MESSAGE;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.*;
import static com.dynonuggets.refonteimplicaction.core.utils.CoreMessages.ERROR_FIELD_VALIDATION_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.utils.UserTestUtils.generateRandomUserDto;
import static java.time.Instant.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = AUTH_BASE_URI;

    static RegisterRequest getValidReggisterRequest() {
        return RegisterRequest.builder()
                .username(randomAlphabetic(5))
                .password(randomAlphabetic(8))
                .firstname("")
                .lastname("")
                .email("un.email@valid.com")
                .build();
    }

    @MockBean
    AuthService authService;
    @MockBean
    RefreshTokenService refreshTokenService;

    @Nested
    @DisplayName("# signup")
    class SignupTest {
        @Test
        @DisplayName("doit répondre OK avec un string et appeler la méthode AutService::signup une fois")
        void should_create_user_when_RegisterRequest_is_valid_and_user_not_exists() throws Exception {
            // given
            final RegisterRequest registerRequest = getValidReggisterRequest();
            willDoNothing().given(authService).signup(registerRequest);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_SIGNUP_URI))
                    .content(toJson(registerRequest))
                    .accept(TEXT_PLAIN)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().string(USER_SIGNUP_SUCCESS_MESSAGE));
            verify(authService, times(1)).signup(any());
        }

        @Nested
        @DisplayName("# Validation")
        class SignupValidationTest {
            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un nom d'utilisateur non valide")
            void should_response_bad_request_when_username_is_not_valid() throws Exception {
                // given
                final RegisterRequest registerRequest = getValidReggisterRequest();
                registerRequest.setUsername("...");
                willDoNothing().given(authService).signup(registerRequest);

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_SIGNUP_URI))
                        .content(toJson(registerRequest))
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errors", hasKey("username")));
                verifyNoInteractions(authService);
            }

            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un mot de passe non valide")
            void should_response_bad_request_when_password_is_not_valid() throws Exception {
                // given
                final RegisterRequest registerRequest = getValidReggisterRequest();
                registerRequest.setPassword("...");
                willDoNothing().given(authService).signup(registerRequest);

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_SIGNUP_URI))
                        .content(toJson(registerRequest))
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errors", hasKey("password")));
                verifyNoInteractions(authService);
            }

            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un email non valide")
            void should_response_bad_request_when_email_is_not_valid() throws Exception {
                // given
                final RegisterRequest registerRequest = getValidReggisterRequest();
                registerRequest.setEmail("...");
                willDoNothing().given(authService).signup(registerRequest);

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_SIGNUP_URI))
                        .content(toJson(registerRequest))
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errors", hasKey("email")));
                verifyNoInteractions(authService);
            }
        }
    }

    @Nested
    @DisplayName("# verifyAccount")
    class VerifyAccountTest {
        @Test
        @DisplayName("doit répondre OK quand une clé de validation valide est fournie")
        void should_response_forbidden_with_validation_key() throws Exception {
            // given
            willDoNothing().given(authService).verifyAccount(anyString());

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(AUTH_ACCOUNT_VERIFICATION_URI), "uneCleDactivation"));

            // then
            resultActions.andExpect(status().isOk());
            verify(authService, times(1)).verifyAccount(anyString());
        }
    }

    @Nested
    @DisplayName("# login")
    class LoginTest {
        @Test
        @DisplayName("doit répondre OK, avec l'utilisateur correspondant quand les identifiants sont corrects")
        void shoud_response_ok_with_corresponding_user_when_credentials_are_corrects() throws Exception {
            // given
            final UserDto userDto = generateRandomUserDto();
            final LoginRequest request = LoginRequest.builder().username(userDto.getUsername()).password(randomAlphabetic(8)).build();
            final LoginResponse response = LoginResponse.builder().authenticationToken(UUID.randomUUID().toString()).refreshToken(UUID.randomUUID().toString()).expiresAt(now()).build();
            given(authService.login(any())).willReturn(response);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_LOGIN_URI))
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticationToken", is(response.getAuthenticationToken())))
                    .andExpect(jsonPath("$.refreshToken", is(response.getRefreshToken())))
                    .andExpect(jsonPath("$.expiresAt", is(response.getExpiresAt().toString())));
            verify(authService, times(1)).login(any());
        }

        @Nested
        @DisplayName("# Validation")
        class LoginValidationTest {
            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un nom d'utilisateur invalide")
            void should_response_bad_request_when_username_is_not_valid() throws Exception {
                final LoginRequest request = LoginRequest.builder()
                        .username(randomAlphabetic(3))
                        .password(randomAlphabetic(8))
                        .build();

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_LOGIN_URI))
                        .content(toJson(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errors", hasKey("username")));
            }

            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un email invalide")
            void should_response_bad_request_when_password_is_not_valid() throws Exception {
                // given
                final LoginRequest request = LoginRequest.builder().username(randomAlphabetic(8)).password(randomAlphabetic(3)).build();

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_LOGIN_URI))
                        .content(toJson(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errors", hasKey("password")));
            }
        }
    }

    @Nested
    @DisplayName("# refreshTokens")
    class RefreshTokensTest {
        @Test
        void should_response_ok_when_sending_valid_RefreshTokenRequest_and_exists() throws Exception {
            // given
            final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().refreshToken(randomAlphabetic(20)).username(randomAlphabetic(10)).build();
            final LoginResponse loginResponse = LoginResponse.builder().refreshToken(randomAlphabetic(20)).authenticationToken(randomAlphabetic(26)).build();
            given(authService.refreshToken(any())).willReturn(loginResponse);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_REFRESH_TOKENS_URI))
                    .content(toJson(refreshTokenRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticationToken", is(loginResponse.getAuthenticationToken())))
                    .andExpect(jsonPath("$.refreshToken", is(loginResponse.getRefreshToken())));
            verify(authService, times(1)).refreshToken(any());
        }

        @Nested
        @DisplayName("# validation")
        class RefreshTokenValidationTest {
            @Test
            @DisplayName("doit répondre BAD_REQUEST avec un nom d'utilisateur non valide")
            void should_response_bad_request_when_username_is_not_valid() throws Exception {
                // given
                final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().refreshToken(randomAlphabetic(20)).build();

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_REFRESH_TOKENS_URI))
                        .content(toJson(refreshTokenRequest))
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errorCode", is(BAD_REQUEST.value())))
                        .andExpect(jsonPath("$.errors", hasKey("username")));
                verifyNoInteractions(authService);
            }

            @Test
            @DisplayName("doit répondre BAD_REQUEST quand le refresh token est non valide")
            void should_response_bad_request_when_refreshToken_is_not_valid() throws Exception {
                // given
                final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().username(randomAlphabetic(10)).build();

                // when
                final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_REFRESH_TOKENS_URI))
                        .content(toJson(refreshTokenRequest))
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage", is(ERROR_FIELD_VALIDATION_MESSAGE)))
                        .andExpect(jsonPath("$.errorCode", is(BAD_REQUEST.value())))
                        .andExpect(jsonPath("$.errors", hasKey("refreshToken")));
                verifyNoInteractions(authService);
            }
        }
    }

    @Nested
    @DisplayName("# logout")
    class LogoutTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec le message attendu quand le refreshTokenRequest est valide")
        void should_response_ok_when_refresh_token_request_is_valid() throws Exception {
            // given
            final RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().refreshToken(randomAlphabetic(20)).username(randomAlphabetic(10)).build();
            willDoNothing().given(refreshTokenService).deleteRefreshToken(any());

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(AUTH_LOGOUT_URI))
                    .content(toJson(refreshTokenRequest))
                    .accept(TEXT_PLAIN)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().string(REFRESH_TOKEN_DELETED_SUCCESSFULLY_MESSAGE));
            verify(refreshTokenService, times(1)).deleteRefreshToken(any());
        }
    }
}
