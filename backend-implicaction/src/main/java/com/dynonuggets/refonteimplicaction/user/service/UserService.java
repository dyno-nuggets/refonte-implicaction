package com.dynonuggets.refonteimplicaction.user.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.service.RoleService;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.event.UserEnabledEvent;
import com.dynonuggets.refonteimplicaction.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USER_ID_NOT_FOUND;


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
                .orElseThrow(() -> new EntityNotFoundException(USERNAME_NOT_FOUND, username));
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
    public void enableUser(final String username) {
        final UserModel user = getUserByUsernameIfExists(username);
        final Role roleUser = roleService.getRoleByName(USER.getLongName());

        user.setEnabled(true);
        user.getRoles().add(roleUser);

        userRepository.save(user);
        publisher.publishEvent(new UserEnabledEvent(this, user.getUsername()));
    }
}
