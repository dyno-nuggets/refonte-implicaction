package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUser;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.springframework.data.domain.Pageable.unpaged;

@TestInstance(Lifecycle.PER_CLASS)
class UserRepositoryTest extends AbstractContainerBaseTest {

    User mockedUser;
    List<User> dbContent;

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void setup() {
        dbContent = userRepository.saveAll(
                of(
                        generateRandomUser(of(RoleEnum.USER), true),
                        generateRandomUser(of(RoleEnum.USER), false),
                        generateRandomUser(of(RoleEnum.USER), true),
                        generateRandomUser(of(RoleEnum.USER), false),
                        generateRandomUser(of(RoleEnum.USER), true)
                )
        );
    }

    @Test
    @DisplayName("doit retourner la liste des utilisateurs dont la date d'activation est nulle")
    void should_find_all_pending_users() {
        // given
        final List<Long> activeUserIds =
                dbContent.stream().filter(u -> u.getActivatedAt() == null).map(User::getId).collect(toList());

        // when
        final Page<User> result = userRepository.findAllByActivatedAtIsNull(unpaged());

        // then
        assertThat(result.getTotalElements()).isNotZero();
        assertThat(result.getContent())
                .allSatisfy(user -> assertThat(user.getActivatedAt()).isNull())
                .map(User::getId)
                .hasSameSizeAs(activeUserIds)
                .containsAll(activeUserIds);
    }
}
