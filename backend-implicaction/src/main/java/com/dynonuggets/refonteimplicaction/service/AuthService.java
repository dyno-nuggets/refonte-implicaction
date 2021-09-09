package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.AuthenticationResponseDto;
import com.dynonuggets.refonteimplicaction.dto.LoginRequestDto;
import com.dynonuggets.refonteimplicaction.dto.RefreshTokenRequestDto;
import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.Signup;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.SignUpRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SignUpRepository signUpRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Enregistre un utilisateur en base de données et lui envoie un mail d'activation
     * crée également une entrée dans la table wp_signups
     *
     * @param reqisterRequest données d'identification de l'utilisateur
     * @throws ImplicactionException si l'envoi du mail échoue
     */
    @Transactional
    public void signupAndSendConfirmation(ReqisterRequestDto reqisterRequest) throws ImplicactionException {
        // TODO: notifier lors de l'existence d'un utilisateur ayant le même mail ou login
        final String activationKey = generateActivationKey();
        final User user = registerUser(reqisterRequest, activationKey);
        registerSignup(activationKey, user);
        mailService.sendUserActivationMail(activationKey, user);
    }

    /**
     * Vérifie existence et l'activation d'une clé d'activation et l'active si elle ne l'est pas déjà
     *
     * @throws ImplicactionException <ul>
     *                               <li>Si la clé n'existe pas</li>
     *                               <li>Si la clé est déjà activée</li>
     *                               </ul>
     */
    public void verifyAccount(String activationKey) throws ImplicactionException {
        Signup signup = signUpRepository.findByActivationKey(activationKey).orElseThrow(() ->
                new ImplicactionException("Activation Key Not Found: " + activationKey));
        if (Boolean.TRUE.equals(signup.getActive())) {
            throw new ImplicactionException("Account With Associated Activation Key Already Activated - " + activationKey);
        }
        activateSignup(signup);
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto) throws ImplicactionException {
        final String username = loginRequestDto.getUsername();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        Instant expiresAt = Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis());
        String refreshToken = refreshTokenService.generateRefreshToken().getToken();

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));

        return AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .username(username)
                .userId(user.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws ImplicactionException {
        final String username = refreshTokenRequestDto.getUsername();

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));

        refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(username);
        Instant expiresAt = Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis());

        return AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequestDto.getRefreshToken())
                .expiresAt(expiresAt)
                .username(username)
                .userId(user.getId())
                .build();
    }

    private void registerSignup(String activationKey, User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Le nom d'utilisateur n'existe pas."));
        Signup signup = Signup.builder()
                .user(userFromDb)
                .registered(user.getRegistered())
                .active(false)
                .activationKey(activationKey)
                .build();
        signUpRepository.save(signup);
    }

    private User registerUser(ReqisterRequestDto reqisterRequest, String activationKey) {
        User user = User.builder()
                .username(reqisterRequest.getUsername())
                .email(reqisterRequest.getEmail())
                .password(passwordEncoder.encode(reqisterRequest.getPassword()))
                .registered(Instant.now())
                .activationKey(activationKey)
                .nicename(reqisterRequest.getNicename())
                .build();
        return userRepository.save(user);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    private void activateSignup(Signup signup) {
        signup.setActivated(Instant.now());
        signup.setActive(true);
        signUpRepository.save(signup);
    }
}
