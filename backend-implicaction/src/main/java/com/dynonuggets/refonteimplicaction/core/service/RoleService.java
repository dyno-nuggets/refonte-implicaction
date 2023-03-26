package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.core.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(final String roleName) {
        final Optional<Role> role = roleRepository.findByName(roleName);
        return role.orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
    }
}
