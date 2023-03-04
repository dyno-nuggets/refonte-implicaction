package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.USER_ID_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;

    /**
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(final Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userAdapter::toDto);
    }

    @Transactional
    public UserDto getUserById(final Long userId) {
        return userAdapter.toDto(getUserByIdIfExists(userId));
    }

    @Transactional
    public UserDto updateUser(final UserDto userDto) {
        final User user = getUserByIdIfExists(userDto.getId());

        // on attribue les valeurs des champs manquants à notre user présent dans la BD avec la conversion
        // vers le modèle de l'adapter des champs modifiés afin de mettre à jour le user entier directement dans la BD
        user.setEmail(userDto.getEmail());

        return userAdapter.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllPendingActivationUsers(final Pageable pageable) {
        return userRepository.findAllByActiveIsFalse(pageable)
                .map(userAdapter::toDto);
    }

    public User getUserByIdIfExists(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException(USER_ID_NOT_FOUND, userId.toString()));
    }
}
