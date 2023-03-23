package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.dto.LoginRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.LoginResponse;
import com.dynonuggets.refonteimplicaction.auth.dto.RefreshTokenRequest;
import com.dynonuggets.refonteimplicaction.auth.dto.RegisterRequest;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.mapper.EmailValidationNotificationMapper;
import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.core.domain.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.notification.service.NotificationService;
import com.dynonuggets.refonteimplicaction.user.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum.USER;
import static java.time.Instant.now;
import static java.util.List.of;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserAdapter userAdapter;
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;
    private final EmailValidationNotificationMapper emailValidationNotificationMapper;


    @Value("${app.url}")
    private String appUrl;


    /**
     * Enregistre un utilisateur en base de données
     *
     * @param registerRequest données d’identification de l’utilisateur
     */
    @Transactional
    public void signup(@Valid final RegisterRequest registerRequest) throws ImplicactionException {
        validateRegisterRequest(registerRequest);
        final String activationKey = generateActivationKey();
        final UserModel user = registerUser(registerRequest, activationKey);
        notificationService.notify(user, emailValidationNotificationMapper);
    }

    /**
     * Vérifie la validité de la requête de sign-up
     *
     * @throws AuthenticationException si le nom d’utilisateur ou l’email est déjà utilisé
     */
    private void validateRegisterRequest(@Valid final RegisterRequest registerRequest) throws ImplicactionException {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AuthenticationException(USERNAME_ALREADY_EXISTS, registerRequest.getUsername());
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AuthenticationException(EMAIL_ALREADY_EXISTS, registerRequest.getEmail());
        }
    }

    /**
     * Définit l’adresse email de l’utilisateur correspondant à la clé d’activation comme vérifié si elle ne l’est pas déjà
     *
     * @throws EntityNotFoundException si aucun n'utilisateur n'est associé à cette clé d'activation
     * @throws AuthenticationException si l’email de l’utilisateur est déjà vérifié
     */
    @Transactional
    public void verifyAccount(final String activationKey) throws ImplicactionException {
        final UserModel user = userRepository.findByActivationKey(activationKey)
                .orElseThrow(() -> new EntityNotFoundException(ACTIVATION_KEY_NOT_FOUND, activationKey));

        if (user.isEmailVerified()) {
            throw new AuthenticationException(USER_MAIL_IS_ALREADY_VERIFIED, user.getUsername());
        }

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    // TODO: revoir potentiellement la logique des jetons (ex: suppression des refresh tokens associés précédemment)
    @Transactional
    public LoginResponse login(@Valid final LoginRequest loginRequest) throws ImplicactionException {
        final String username = loginRequest.getUsername();
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        final String token = jwtProvider.generateToken(authenticate);
        final Instant expiresAt = now().plusMillis(jwtProvider.getJwtExpirationInMillis());
        final String refreshToken = refreshTokenService.generateRefreshToken().getToken();

        final UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(BAD_CREDENTIALS));

        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .currentUser(userAdapter.toDtoLight(user))
                .build();
    }

    public UserModel getCurrentUser() {
        final org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserByUsernameIfExists(principal.getUsername());
    }

    @Transactional(readOnly = true)
    public LoginResponse refreshToken(final RefreshTokenRequest refreshTokenRequest) throws ImplicactionException {
        final String username = refreshTokenRequest.getUsername();

        // une exception sera levée si l'utilisateur ou le refresh token n'existe pas
        final UserModel user = userService.getUserByUsernameIfExists(username);
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        final String token = jwtProvider.generateTokenWithUsername(username);
        final Instant expiresAt = now().plusMillis(jwtProvider.getJwtExpirationInMillis());

        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(expiresAt)
                .currentUser(userAdapter.toDtoLight(user))
                .build();
    }

    private UserModel registerUser(final RegisterRequest registerRequest, final String activationKey) {
        final List<Role> roles = roleRepository.findAllByNameIn(of(USER.name()));
        final UserModel user = UserModel.builder()
                .username(registerRequest.getUsername())
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .registeredAt(now())
                .enabled(false)
                .emailVerified(false)
                .activationKey(activationKey)
                .registeredAt(now())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    public boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    /**
     * Vérifie que le nom d’utilisateur en paramètre correspond à l’utilisateur courant OU que l’utilisateur courant est admin
     * TODO: refacto cette méthode en prenant en paramètre une liste de rôles autorisés
     *
     * @param username le nom de l’utilisateur dont il faut vérifier l’autorisation
     * @throws AuthenticationException si l’utilisateur n’est pas autorisé
     */
    @Transactional(readOnly = true)
    public void verifyAccessIsGranted(@NonNull final String username) {
        final UserModel currentUser = getCurrentUser();
        final String currentUsername = callIfNotNull(currentUser, UserModel::getUsername);
        if (!(StringUtils.equals(currentUsername, username) || isTrue(callIfNotNull(currentUser, UserModel::isAdmin)))) {
            throw new CoreException(OPERATION_NOT_PERMITTED);
        }
    }
}
