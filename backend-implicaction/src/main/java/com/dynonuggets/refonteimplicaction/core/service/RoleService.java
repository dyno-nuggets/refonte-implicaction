package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.user.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.user.domain.repository.RoleRepository;
import com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleModel getRoleByName(final RoleEnum roleEnum) {
        final Optional<RoleModel> role = roleRepository.findByName(roleEnum);
        return role.orElseGet(() -> roleRepository.save(RoleModel.builder().name(roleEnum).build()));
    }
}
