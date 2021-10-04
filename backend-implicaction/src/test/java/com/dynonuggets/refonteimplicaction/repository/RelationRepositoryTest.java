package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
class RelationRepositoryTest extends AbstractContainerBaseTest {

    User sender;
    User receiver;
    User unRelated;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationRepository relationRepository;

    @BeforeEach
    void setUp() {
        sender = User.builder()
                .username("sender")
                .email("sender@email.com")
                .registeredAt(Instant.now())
                .build();
        receiver = User.builder()
                .username("receiver")
                .email("receiver@email.com")
                .registeredAt(Instant.now())
                .build();

        unRelated = User.builder()
                .username("unRelated")
                .email("unrelated@email.com")
                .registeredAt(Instant.now())
                .build();

        sender = userRepository.save(sender);
        receiver = userRepository.save(receiver);
        unRelated = userRepository.save(unRelated);
    }

    @Test
    void shouldSaveRelation() {

        Relation relation = Relation.builder()
                .sender(sender)
                .receiver(receiver)
                .sentAt(Instant.now())
                .confirmedAt(Instant.now().plusMillis(1000L))
                .build();

        final Relation save = relationRepository.save(relation);

        assertThat(save).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(relation);

        assertThat(relationRepository.isInRelation(sender.getId(), receiver.getId())).isTrue();
        assertThat(relationRepository.isInRelation(sender.getId(), unRelated.getId())).isFalse();
        assertThat(relationRepository.isInRelation(unRelated.getId(), receiver.getId())).isFalse();
    }
}
