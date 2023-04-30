package com.dynonuggets.refonteimplicaction.core.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.RoleModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.domain.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum.ROLE_PREMIUM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleService roleService;

    @Captor
    ArgumentCaptor<RoleModel> captor;

    @Nested
    @DisplayName("# getRoleByName")
    class GetRoleByNameTests {
        @Test
        @DisplayName("doit retourner un rôle quand le rôle existe")
        void should_return_role_when_exists() {
            // given
            final RoleEnum rolePremium = ROLE_PREMIUM;
            final RoleModel expectedRole = RoleModel.builder().name(rolePremium).build();
            given(roleRepository.findByName(rolePremium)).willReturn(Optional.of(expectedRole));

            // when
            final RoleModel actualRole = roleService.getRoleByName(rolePremium);

            // then
            assertThat(actualRole).isEqualTo(expectedRole);
            verify(roleRepository, times(1)).findByName(rolePremium);
            verify(roleRepository, never()).save(any());
        }


        @Test
        @DisplayName("doit créer le rôle et le retourner quand le rôle n'existe pas")
        void should_create_role_when_not_exists() {
            // given
            final RoleEnum rolePremium = ROLE_PREMIUM;
            final RoleModel expectedRole = RoleModel.builder().name(rolePremium).build();
            given(roleRepository.findByName(rolePremium)).willReturn(Optional.empty());
            given(roleRepository.save(expectedRole)).willReturn(expectedRole);

            // when
            final RoleModel actualRole = roleService.getRoleByName(rolePremium);

            // then
            verify(roleRepository, times(1)).findByName(rolePremium);
            verify(roleRepository, times(1)).save(captor.capture());
            assertThat(actualRole).isEqualTo(expectedRole);
            assertThat(captor.getValue()).isEqualTo(expectedRole);
        }
    }
}
