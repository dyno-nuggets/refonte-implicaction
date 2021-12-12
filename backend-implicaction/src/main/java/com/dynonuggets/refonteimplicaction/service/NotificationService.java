package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.JOB_ACTIVATION;
import static com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum.POST_CREATION;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final NotificationRepository notificationRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    @Value("${app.url}")
    private String appUrl;

    public void createJobNotification(final JobPosting jobPosting) {
        List<User> users = userRepository.findAll();
        String url = String.format("%s/jobs/%s", appUrl, jobPosting.getId());
        final String message = String.format("Une nouvelle offre a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, jobPosting.getTitle());
        final String title = "[Implic'action] Une nouvelle offre a été publiée";
        createNotification(users, message, title, JOB_ACTIVATION);
    }

    public void createPostNotification(final Post post) {
        final List<User> users = new ArrayList<>(post.getGroup().getUsers());
        String url = String.format("%s/discussions/%s", appUrl, post.getId());
        final String message = String.format("Une nouvelle discussion a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, post.getName());
        final String title = "[Implic'action] Une nouvelle discussion a été publiée";
        createNotification(users, message, title, POST_CREATION);
    }

    private void createNotification(List<User> users, String message, String title, NotificationTypeEnum type) {
        if (users.isEmpty()) {
            return;
        }

        Notification notification = Notification.builder()
                .type(type)
                .users(users)
                .date(Instant.now())
                .sent(false)
                .read(false)
                .message(message)
                .title(title)
                .build();
        final Notification notificationSave = notificationRepository.save(notification);
        users.forEach(user -> user.getNotifications().add(notificationSave));
        userRepository.saveAll(users);
    }

    @Scheduled(fixedDelay = 5000)
    protected void doNotify() {
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
