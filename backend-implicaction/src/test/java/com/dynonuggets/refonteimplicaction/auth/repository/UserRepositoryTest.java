package com.dynonuggets.refonteimplicaction.auth.repository;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.repository.AbstractContainerBaseTest;
import lombok.var;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtils.generateRandomUser;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.springframework.data.domain.Pageable.unpaged;

@TestInstance(Lifecycle.PER_CLASS)
class UserRepositoryTest extends AbstractContainerBaseTest {

    List<User> dbContent;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        dbContent = userRepository.saveAll(
                List.of(
                        generateRandomUser(of(USER), true),
                        generateRandomUser(of(USER), false),
                        generateRandomUser(of(USER), true),
                        generateRandomUser(of(USER), false),
                        generateRandomUser(of(USER), true)
                )
        );
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("doit retourner la liste des utilisateurs dont la date d'activation est nulle")
    void should_find_all_pending_users() {
        // given
        final List<Long> activeUserIds =
                dbContent.stream().filter(u -> !u.isActive()).map(User::getId).collect(toList());

        // when
        final Page<User> result = userRepository.findAllByActiveIsFalse(unpaged());

        // then
        assertThat(result.getContent())
                .allSatisfy(user -> assertThat(user.isActive()).isFalse())
                .hasSameSizeAs(activeUserIds)
                .map(User::getId)
                .containsAll(activeUserIds);
    }

    @Nested
    @DisplayName("# findByActivationKey")
    class FindByActivationKeyTest {
        @Test
        @DisplayName("doit retourner l'utilisateur correspondant à la clé d'activation transmise")
        void should_find_user_by_activation_key_when_exists() {
            // given
            final var expectedUser = dbContent.get(0);

            // when
            final Optional<User> actualUser = userRepository.findByActivationKey(expectedUser.getActivationKey());

            // when
            assertThat(actualUser)
                    .isPresent().get()
                    .hasFieldOrPropertyWithValue("id", expectedUser.getId())
                    .hasFieldOrPropertyWithValue("activationKey", expectedUser.getActivationKey());
        }

        @Test
        @DisplayName("doit retourner Optional.EMPTY quand la clé d'activation transmise ne correspond à aucun utilisateur")
        void should_not_find_user_by_activationKey_when_not_exists() {
            assertThat(userRepository.findByActivationKey("qui n'existe pas !"))
                    .isNotPresent();
        }
    }

    @Nested
    @DisplayName("# findByUsername")
    class FindByUsernameTest {
        @Test
        @DisplayName("doit retourner l'utilisateur correspondant au nom d'utilisateur transmis")
        void should_find_user_by_username_when_exists() {
            // given
            final var expectedUser = dbContent.get(0);

            // when
            final Optional<User> actualUser = userRepository.findByUsername(expectedUser.getUsername());

            // when
            assertThat(actualUser)
                    .isPresent().get()
                    .hasFieldOrPropertyWithValue("id", expectedUser.getId())
                    .hasFieldOrPropertyWithValue("username", expectedUser.getUsername());
        }

        @Test
        @DisplayName("doit retourner Optional.EMPTY quand le nom d'utilisateur transmis ne correspond à aucun utilisateur")
        void should_not_find_user_by_username_when_not_exists() {
            assertThat(userRepository.findByUsername("qui n'existe pas !"))
                    .isNotPresent();
        }
    }

    @Nested
    @DisplayName("# existsByUsername")
    class ExistsByUsernameTest {
        @Test
        @DisplayName("doit retourner VRAI quand l'utilisateur existe")
        void should_return_true_when_user_exists() {
            assertThat(userRepository.existsByUsername(dbContent.get(0).getUsername()))
                    .isTrue();
        }

        @Test
        @DisplayName("doit retourner FAUX quand l'utilisateur n'existe pas")
        void should_return_false_when_user_not_exists() {
            assertThat(userRepository.existsByUsername("qui n'existe pas !"))
                    .isFalse();
        }
    }

    @Nested
    @DisplayName("# existsByUsername")
    class ExistsByEmailTest {
        @Test
        @DisplayName("doit retourner VRAI quand l'utilisateur existe")
        void should_return_true_when_user_exists() {
            assertThat(userRepository.existsByEmail(dbContent.get(0).getEmail()))
                    .isTrue();
        }

        @Test
        @DisplayName("doit retourner FAUX quand l'utilisateur n'existe pas")
        void should_return_false_when_user_not_exists() {
            assertThat(userRepository.existsByEmail("qui n'existe pas !"))
                    .isFalse();
        }
    }
}
