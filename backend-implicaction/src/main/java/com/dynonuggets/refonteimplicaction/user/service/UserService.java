package com.dynonuggets.refonteimplicaction.user.service;

import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USERNAME_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.user.error.UserErrorResult.USER_ID_NOT_FOUND;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(final Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserDto getUserById(final Long userId) {
        return userMapper.toDto(getUserByIdIfExists(userId));
    }

    @Transactional
    public UserDto updateUser(final UserDto userDto) {
        final UserModel user = getUserByIdIfExists(userDto.getId());

        // on attribue les valeurs des champs manquants à notre user présent dans la BD avec la conversion
        // vers le modèle de l'adapter des champs modifiés afin de mettre à jour le user entier directement dans la BD
        user.setEmail(userDto.getEmail());

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllPendingActivationUsers(final Pageable pageable) {
        return userRepository.findAllByEnabledIsFalse(pageable)
                .map(userMapper::toDto);
    }

    public UserModel getUserByUsernameIfExists(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USERNAME_NOT_FOUND, username));
    }

    public UserModel getUserByIdIfExists(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOT_FOUND, userId.toString()));
    }

    public void enableUser(final String username) {
        final UserModel user = getUserByUsernameIfExists(username);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
