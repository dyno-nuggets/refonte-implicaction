package com.dynonuggets.refonteimplicaction.auth.controller;

import com.dynonuggets.refonteimplicaction.auth.dto.LoginRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.LoginResponse;
import com.dynonuggets.refonteimplicaction.auth.dto.RefreshTokenRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.RegisterRequest;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.auth.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.REFRESH_TOKEN_DELETED_SUCCESSFULLY_MESSAGE;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthMessages.USER_SIGNUP_SUCCESS_MESSAGE;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(AUTH_BASE_URI)
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(AUTH_SIGNUP_URI)
    public ResponseEntity<String> signup(@RequestBody @Valid final RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(USER_SIGNUP_SUCCESS_MESSAGE, OK);
    }

    /**
     * @param activationKey la clé d’activation correspondant à l'utilisateur dont on souhaite valider l'adresse email
     * @return réponse ok {@link org.springframework.http.HttpStatus#OK}
     */
    @GetMapping(AUTH_ACCOUNT_VERIFICATION_URI)
    public ResponseEntity<Void> verifyAccount(@PathVariable final String activationKey) {
        authService.verifyAccount(activationKey);
        return ResponseEntity.ok().build();
    }

    @PostMapping(AUTH_LOGIN_URI)
    public LoginResponse login(@RequestBody @Valid final LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(AUTH_REFRESH_TOKENS_URI)
    public LoginResponse refreshTokens(@RequestBody @Valid final RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping(AUTH_LOGOUT_URI)
    public ResponseEntity<String> logout(@RequestBody @Valid final RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(REFRESH_TOKEN_DELETED_SUCCESSFULLY_MESSAGE);
    }
}
