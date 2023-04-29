package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.event.UserEnabledEvent;
import com.dynonuggets.refonteimplicaction.core.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum.ROLE_USER;
import static com.dynonuggets.refonteimplicaction.core.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.error.UserErrorResult.USER_ID_NOT_FOUND;


@Service
@AllArgsConstructor
public class UserService {
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    /**
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(final Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(final Long userId) {
        return userMapper.toDto(getUserByIdIfExists(userId));
    }

    /**
     * @return tous les utilisateurs qui sont en attente de validation
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllPendingActivationUsers(final Pageable pageable) {
        return userRepository.findAllByEnabledIsFalse(pageable)
                .map(userMapper::toDto);
    }

    /**
     * @return l'utilisateur correspondant au nom d’utilisateur fourni en paramètres
     * @throws EntityNotFoundException si l’utilisateur n’existe pas
     */
    @Transactional(readOnly = true)
    public UserModel getUserByUsernameIfExists(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USERNAME_NOT_FOUND));
    }

    /**
     * @return l'utilisateur ayant pour id userId
     * @throws EntityNotFoundException si l’utilisateur n’existe pas
     */
    @Transactional(readOnly = true)
    public UserModel getUserByIdIfExists(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOT_FOUND, userId.toString()));
    }

    @Transactional
    public UserDto enableUser(final String username) {
        final UserModel user = getUserByUsernameIfExists(username);
        final RoleModel roleUser = roleService.getRoleByName(ROLE_USER);

        user.setEnabled(true);
        user.getRoles().add(roleUser);

        userRepository.save(user);
        publisher.publishEvent(new UserEnabledEvent(this, user.getUsername()));

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUserRoles(final String username, final Set<RoleEnum> roles) {
        final UserModel user = getUserByUsernameIfExists(username);
        final Set<RoleModel> rolesModels = roles.stream().map(roleService::getRoleByName).collect(Collectors.toSet());

        user.setRoles(rolesModels);
        
        return userMapper.toDto(userRepository.save(user));
    }
}
