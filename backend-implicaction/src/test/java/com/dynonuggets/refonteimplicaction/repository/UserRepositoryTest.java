package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
class UserRepositoryTest extends AbstractContainerBaseTest {

    final int DEFAULT_PAGE = 0;
    final int DEFAULT_SIZE = 10;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        User user = User.builder()
                .username("test user")
                .password("secret password")
                .email("user@email.com")
                .registeredAt(Instant.now())
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    void shouldFindUser() {
        User user = User.builder()
                .username("test get user")
                .password("secret password")
                .email("userget@email.com")
                .registeredAt(Instant.now())
                .build();

        userRepository.save(user);

        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        assertThat(byUsername).isPresent();
        assertThat(byUsername.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    void shouldFindAllPendingActivationUsers() {
        List<User> users = Arrays.stream(new String[]{"unactivated1", "unactivated2", "activated1", "activated2", "unactivated3"})
                .map(username -> {
                    boolean isActivated = !username.contains("unactivated");
                    return User.builder()
                            .username(username)
                            .email(username + "@mail.com")
                            .activatedAt(isActivated ? Instant.now() : null)
                            .active(isActivated)
                            .build();
                })
                .collect(Collectors.toList());

        final User[] expecteds = users.stream()
                .filter(user -> !user.isActive())
                .toArray(User[]::new);

        userRepository.saveAll(users);

        Pageable pageable = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);
        final Page<User> actuals = userRepository.findAllByActivatedAtIsNull(pageable);

        assertThat(actuals.getTotalElements()).isEqualTo(expecteds.length);
        assertThat(actuals.getContent()).usingElementComparatorIgnoringFields("id")
                .contains(expecteds);
    }
}
