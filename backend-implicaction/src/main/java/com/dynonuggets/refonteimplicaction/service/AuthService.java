package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.AuthenticationResponseDto;
import com.dynonuggets.refonteimplicaction.dto.LoginRequestDto;
import com.dynonuggets.refonteimplicaction.dto.RefreshTokenRequestDto;
import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.JobSeekerRepository;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import com.dynonuggets.refonteimplicaction.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.USER_ACTIVATION;
import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.USER_REGISTRATION;
import static com.dynonuggets.refonteimplicaction.utils.Message.*;
import static java.util.stream.Collectors.toList;

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
    private final JobSeekerRepository jobSeekerRepository;
    private final NotificationRepository notificationRepository;


    @Value("${app.url}")
    private String appUrl;


    /**
     * Enregistre un utilisateur en base de données et lui envoie un mail d'activation
     * crée également une entrée dans la table wp_signups
     *
     * @param reqisterRequest données d'identification de l'utilisateur
     * @throws ImplicactionException si l'envoi du mail échoue
     */
    @Transactional
    public void signup(ReqisterRequestDto reqisterRequest) throws ImplicactionException {
        validateRegisterRequest(reqisterRequest);
        final String activationKey = generateActivationKey();
        final List<User> admins = userRepository.findAllByRoles_NameIn(Collections.singletonList(RoleEnum.ADMIN.getLongName()));
        registerUser(reqisterRequest, activationKey);
        final Notification notification = Notification.builder()
                .message(String.format(USER_REGISTER_MAIL_BODY, reqisterRequest.getUsername()))
                .sent(false)
                .read(false)
                .date(Instant.now())
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
    private void validateRegisterRequest(ReqisterRequestDto reqisterRequest) {
        userRepository.findAllByUsernameOrEmail(reqisterRequest.getUsername(), reqisterRequest.getEmail())
                .forEach(user -> {
                    String message = user.getUsername().equals(reqisterRequest.getUsername()) ?
                            USERNAME_ALREADY_EXISTS_MESSAGE : EMAIL_ALREADY_EXISTS_MESSAGE;
                    throw new UnauthorizedException(message);
                });
    }

    /**
     * Vérifie existence et l'activation d'une clé d'activation et l'active si elle ne l'est pas déjà
     *
     * @throws ImplicactionException Si la clé n'existe pas, ou si la clé est déjà activée
     */
    @Transactional
    public void verifyAccount(String activationKey) throws ImplicactionException {
        User user = userRepository.findByActivationKey(activationKey)
                .orElseThrow(() -> new ImplicactionException(String.format(ACTIVATION_KEY_NOT_FOUND_MESSAGE, activationKey)));

        if (user.getActivatedAt() != null) {
            throw new ImplicactionException("Account With Associated Activation Key Already Activated - " + activationKey);
        }

        user.setActivatedAt(Instant.now());
        user.setActive(true);

        Notification notification = Notification.builder()
                .type(USER_ACTIVATION)
                .users(Collections.singletonList(user))
                .date(Instant.now())
                .sent(false)
                .read(false)
                .message(String.format("Félicitation, votre compte <a href=\"%s/auth/login\">implicaction</a> est désormais actif.", appUrl))
                .title("[Implicaction] Activation de votre compte")
                .build();
        final Notification notificationSave = notificationRepository.save(notification);
        user.getNotifications().add(notificationSave);

        user = userRepository.save(user);

        final List<String> userRoles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList());

        // à l'activation du compte on map l'utilisateur à un job_seeker
        if (userRoles.contains(RoleEnum.JOB_SEEKER.getLongName())) {
            JobSeeker jobSeeker = JobSeeker.builder()
                    .user(user)
                    .build();
            jobSeekerRepository.save(jobSeeker);
        }
    }

    @Transactional
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
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));

        return AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .currentUser(userAdapter.toDtoLight(user))
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Transactional(readOnly = true)
    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws ImplicactionException {
        final String username = refreshTokenRequestDto.getUsername();
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));

        refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(username);
        Instant expiresAt = Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis());

        return AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequestDto.getRefreshToken())
                .expiresAt(expiresAt)
                .currentUser(userAdapter.toDtoLight(user))
                .build();
    }

    private User registerUser(ReqisterRequestDto reqisterRequest, String activationKey) {
        final List<Role> roles = roleRepository.findAllByNameIn(reqisterRequest.getRoles());

        User user = User.builder()
                .username(reqisterRequest.getUsername())
                .email(reqisterRequest.getEmail())
                .password(passwordEncoder.encode(reqisterRequest.getPassword()))
                .firstname(reqisterRequest.getFirstname())
                .lastname(reqisterRequest.getLastname())
                .registeredAt(Instant.now())
                .active(false)
                .activationKey(activationKey)
                .registeredAt(Instant.now())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
