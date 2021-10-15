package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Notification;
import com.dynonuggets.refonteimplicaction.model.TypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void shouldSaveNotification() {
        Notification expectedNotification = Notification.builder()
                .sender(null)
                .receiver(null)
                .message("Vous ajouté en ami")
                .date(Instant.now())
                .type(TypeEnum.RELATION_ADD_NOTIFICATION.getCode())
                .read(true)
                .build();

        Notification actualNotification = notificationRepository.save(expectedNotification);

        assertThat(actualNotification).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedNotification);

        assertThat(actualNotification.getId()).isNotNull();
    }

    @Test
    void shouldFindNotification() {
        Notification expectedNotification = Notification.builder()
                .sender(null)
                .receiver(null)
                .message("Vous ajouté en ami")
                .date(Instant.now())
                .type(TypeEnum.RELATION_ADD_NOTIFICATION.getCode())
                .read(true)
                .build();

        Notification notificationSave = notificationRepository.save(expectedNotification);

        final Optional<Notification> notificationFound = notificationRepository.findById(notificationSave.getId());

        assertThat(notificationFound).isPresent();

        assertThat(notificationFound.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedNotification);

        assertThat(notificationFound.get().getId()).isEqualTo(notificationSave.getId());
    }

}
