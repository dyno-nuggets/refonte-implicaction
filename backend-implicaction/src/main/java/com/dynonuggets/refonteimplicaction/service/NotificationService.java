package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.core.domain.model.User;
import com.dynonuggets.refonteimplicaction.core.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.Notification;
import com.dynonuggets.refonteimplicaction.model.NotificationTypeEnum;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
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
        final List<User> users = userRepository.findAll();
        final String url = String.format("%s/jobs/%s", appUrl, jobPosting.getId());
        final String message = String.format("Une nouvelle offre a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, jobPosting.getTitle());
        final String title = "[Implic'action] Une nouvelle offre a été publiée";
        createNotification(users, message, title, JOB_ACTIVATION);
    }

    public void createPostNotification(final Post post) {
        final List<User> users = post.getGroup().getProfiles().stream().map(Profile::getUser).collect(toList());
        final String url = String.format("%s/discussions/%s", appUrl, post.getId());
        final String message = String.format("Une nouvelle discussion a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, post.getName());
        final String title = "[Implic'action] Une nouvelle discussion a été publiée";
        createNotification(users, message, title, POST_CREATION);
    }

    private void createNotification(final List<User> users, final String message, final String title, final NotificationTypeEnum type) {
        if (users.isEmpty()) {
            return;
        }

        final Notification notification = Notification.builder()
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
        final TypedQuery<Notification> q = entityManager.createQuery("select distinct n from Notification n left join fetch n.users where n.sent = false", Notification.class);
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
