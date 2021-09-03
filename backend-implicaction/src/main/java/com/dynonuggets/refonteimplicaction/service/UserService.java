package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;

    @Transactional(readOnly = true)
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userAdapter::toDto);
    }

    public UserDto getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                new UsernameNotFoundException("No user found with id " + userId));
        return userAdapter.toDto(user);
    }
}
