package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;

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
}
