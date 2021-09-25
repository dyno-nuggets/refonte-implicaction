package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.*;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.model.JobSeeker;
import com.dynonuggets.refonteimplicaction.model.Role;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.JobSeekerRepository;
import com.dynonuggets.refonteimplicaction.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private static final String USERNAME_ALREADY_EXISTS_MSG = "Un compte utilisateur existe déjà avec ce nom d'utilisateur.";
    private static final String EMAIL_ALREADY_EXISTS_MSG = "Un compte utilisateur existe déjà avec cette adresse email.";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserAdapter userAdapter;
    private RoleRepository roleRepository;
    private JobSeekerRepository jobSeekerRepository;

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
        final User user = registerUser(reqisterRequest, activationKey);
        mailService.sendUserActivationMail(activationKey, user);
    }

    /**
     * Vérifie la validité de la requête de sign-up
     */
    private void validateRegisterRequest(ReqisterRequestDto reqisterRequest) {
        userRepository.findAllByUsernameOrEmail(reqisterRequest.getUsername(), reqisterRequest.getEmail())
                .forEach(user -> {
                    String message = user.getUsername().equals(reqisterRequest.getUsername()) ?
                            USERNAME_ALREADY_EXISTS_MSG : EMAIL_ALREADY_EXISTS_MSG;
                    throw new UnauthorizedException(message);
                });
    }

    /**
     * Vérifie existence et l'activation d'une clé d'activation et l'active si elle ne l'est pas déjà
     *
     * @throws ImplicactionException <ul>
     *                               <li>Si la clé n'existe pas</li>
     *                               <li>Si la clé est déjà activée</li>
     *                               </ul>
     */
    @Transactional
    public void verifyAccount(String activationKey) throws ImplicactionException {
        User user = userRepository.findByActivationKey(activationKey)
                .orElseThrow(() -> new ImplicactionException("Activation Key Not Found: " + activationKey));
        if (user.getActivatedAt() != null) {
            throw new ImplicactionException("Account With Associated Activation Key Already Activated - " + activationKey);
        }

        user.setActivatedAt(Instant.now());
        user.setActive(true);
        user = userRepository.save(user);

        final List<String> userRoles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList());

        // à l'activation du compte on map l'utilisateur à un job_seeker
        if (userRoles.contains(RoleEnum.JOB_SEEKER.getLabel())) {
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

        final Set<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(toSet());

        return AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .username(username)
                .userId(user.getId())
                .roles(roleNames)
                .build();
    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
        return userAdapter.toDto(user);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws ImplicactionException {
        final String username = refreshTokenRequestDto.getUsername();
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));

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
}
