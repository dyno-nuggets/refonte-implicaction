package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.LoginRequest;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.LoginResponse;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.RefreshTokenRequest;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.RegisterRequest;
import com.dynonuggets.refonteimplicaction.auth.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.model.Notification;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.ADMIN;
import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.*;
import static com.dynonuggets.refonteimplicaction.core.util.Message.USER_REGISTER_MAIL_BODY;
import static com.dynonuggets.refonteimplicaction.core.util.Message.USER_REGISTER_MAIL_TITLE;
import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.USER_ACTIVATION;
import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.USER_REGISTRATION;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.Collections.singletonList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserAdapter userAdapter;
    private final RoleRepository roleRepository;
    private final NotificationRepository notificationRepository;


    @Value("${app.url}")
    private String appUrl;

    /**
     * Enregistre un utilisateur en base de données et lui envoie un mail d'activation
     * crée également une entrée dans la table wp_signups
     *
     * @param reqisterRequest données d'identification de l'utilisateur
     * @throws AuthenticationException si l'envoi du mail échoue
     */
    @Transactional
    public void signup(@Valid final RegisterRequest reqisterRequest) throws ImplicactionException {
        validateRegisterRequest(reqisterRequest);
        final String activationKey = generateActivationKey();
        final List<User> admins = userRepository.findAllByRoles_NameIn(singletonList(ADMIN.getLongName()));
        registerUser(reqisterRequest, activationKey);

        // TODO: MAIL-NOTIFICATION à revoir
        final Notification notification = Notification.builder()
                .message(format(USER_REGISTER_MAIL_BODY, reqisterRequest.getUsername()))
                .sent(false)
                .read(false)
                .date(now())
                .title(USER_REGISTER_MAIL_TITLE)
                .type(USER_REGISTRATION)
                .build();

        final Notification save = notificationRepository.save(notification);
        admins.forEach(admin -> admin.getNotifications().add(save));
        userRepository.saveAll(admins);
    }

    /**
     * Vérifie la validité de la requête de sign-up
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
     * Vérifie existence et l'activation d'une clé d'activation et l'active si elle ne l'est pas déjà
     *
     * @throws AuthenticationException Si la clé n'existe pas, ou si la clé est déjà activée
     */
    @Transactional
    public void verifyAccount(final String activationKey) throws ImplicactionException {
        final User user = userRepository.findByActivationKey(activationKey)
                .orElseThrow(() -> new AuthenticationException(ACTIVATION_KEY_NOT_FOUND, activationKey));

        if (user.isActive()) {
            throw new AuthenticationException(USER_ALREADY_ACTIVATED, activationKey);
        }

        user.setActive(true);

        // TODO: MAIL-NOTIFICATION à revoir
        final Notification notification = Notification.builder()
                .type(USER_ACTIVATION)
                .users(singletonList(user))
                .date(now())
                .sent(false)
                .read(false)
                .message(format("Félicitation, votre compte <a href=\"%s/auth/login\">implicaction</a> est désormais actif.", appUrl))
                .title("[Implicaction] Activation de votre compte")
                .build();
        final Notification notificationSave = notificationRepository.save(notification);
        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>());
        }
        user.getNotifications().add(notificationSave);
        userRepository.save(user);
    }

    // TODO: revoir potentiellement la logique des tokens (ex: suppression des refresh tokens associés précédemment)
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

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));

        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .currentUser(userAdapter.toDtoLight(user))
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        final org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserIfExists(principal.getUsername());
    }

    @Transactional(readOnly = true)
    public LoginResponse refreshToken(final RefreshTokenRequest refreshTokenRequest) throws ImplicactionException {
        final String username = refreshTokenRequest.getUsername();
        final User user = getUserIfExists(username);

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

    private User getUserIfExists(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
    }

    private void registerUser(final RegisterRequest reqisterRequest, final String activationKey) {
        final List<Role> roles = roleRepository.findAllByNameIn(List.of(USER.name()));

        final User user = User.builder()
                .username(reqisterRequest.getUsername())
                .email(reqisterRequest.getEmail())
                .password(passwordEncoder.encode(reqisterRequest.getPassword()))
                .firstname(reqisterRequest.getFirstname())
                .lastname(reqisterRequest.getLastname())
                .registeredAt(now())
                .active(false)
                .activationKey(activationKey)
                .registeredAt(now())
                .roles(roles)
                .build();
        userRepository.save(user);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    public boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Transactional(readOnly = true)
    public void ensureCurrentUserAllowed(RoleEnum... roles) {
        User currentUser = getCurrentUser();

        List<String> requiredRoles = Arrays.stream(roles).map(RoleEnum::getLongName).collect(toList());

        boolean isAllowed = currentUser.getRoles().stream()
                .anyMatch(role -> requiredRoles.contains(role.getName()));
        if (!isAllowed) {
            throw new UnauthorizedException("Vous n'avez pas le droit de faire ça");
        }
    }
}
