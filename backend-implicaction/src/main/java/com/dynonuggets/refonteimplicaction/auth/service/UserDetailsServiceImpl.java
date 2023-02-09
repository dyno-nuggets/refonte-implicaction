package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.USER_IS_NOT_ACTIVATED;
import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.USER_NOT_FOUND;
import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));

        if (!user.isActive()) {
            throw new AuthenticationException(USER_IS_NOT_ACTIVATED);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true,
                true, true, getAuthorities(user)
        );
    }

    private Set<? extends GrantedAuthority> getAuthorities(final User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toSet());
    }
}
