package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.core.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleModel getRoleByName(final String roleName) {
        final Optional<RoleModel> role = roleRepository.findByName(roleName);
        return role.orElseGet(() -> roleRepository.save(RoleModel.builder().name(roleName).build()));
    }
}
