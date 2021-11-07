package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.AuthenticationResponseDto;
import com.dynonuggets.refonteimplicaction.dto.LoginRequestDto;
import com.dynonuggets.refonteimplicaction.dto.RefreshTokenRequestDto;
import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ReqisterRequestDto reqisterRequest) throws ImplicactionException {
        authService.signup(reqisterRequest);
        return new ResponseEntity<>("User registration successful", OK);
    }

    @GetMapping("accountVerification/{activationKey}")
    public ResponseEntity<Void> verifyAccount(@PathVariable String activationKey) throws ImplicactionException {
        authService.verifyAccount(activationKey);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws ImplicactionException {
        return authService.login(loginRequestDto);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponseDto refreshTokens(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequest) throws ImplicactionException {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok("Refresh Token Deleted Successfully");
    }
}
