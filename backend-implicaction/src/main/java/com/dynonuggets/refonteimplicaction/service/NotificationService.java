package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.model.Notification;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final NotificationRepository notificationRepository;
    private final EntityManager entityManager;

    @Scheduled(fixedDelay = 5000)
    private void doNotify() {
        // TODO: déplacer cette logique dans un repositoryCustom
        TypedQuery<Notification> q = entityManager.createQuery("select distinct n from Notification n left join fetch n.users where n.sent = false", Notification.class);
        final List<Notification> notifications = q.getResultList();

        notifications.forEach(notification -> {
            final List<String> collect = notification.getUsers()
                    .stream()
                    .map(User::getEmail)
                    .collect(toList());

            final NotificationEmailDto notificationEmailDto = NotificationEmailDto.builder()
                    .body(notification.getMessage())
                    .recipients(collect)
                    .subject(notification.getTitle())
                    .build();

            notification.setSent(true);

            mailService.sendMail(notificationEmailDto);

        });
        notificationRepository.saveAll(notifications);
    }

}
