package com.dynonuggets.refonteimplicaction.core.notification.service;

import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.core.notification.model.EmailObject;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.core.user.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import com.dynonuggets.refonteimplicaction.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.dynonuggets.refonteimplicaction.core.notification.model.enums.MailTemplateEnum.DEFAULT_TEMPLATE;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.emptyStreamIfNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final UserRepository userRepository;

    @Value("${app.url}")
    private String appUrl;

    @Deprecated(since = "2023.03")
    public void createJobNotification(final JobPosting jobPosting) {
        final List<User> users = userRepository.findAll();
        final String url = format("%s/jobs/%s", appUrl, jobPosting.getId());
        final String message = format("Une nouvelle offre a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, jobPosting.getTitle());
        final String title = "[Implic'action] Une nouvelle offre a été publiée";
        final EmailObject emailObject = createNotification(users, message, title);

        if (emailObject != null) {
            mailService.sendMail(DEFAULT_TEMPLATE, emailObject);
        }
    }

    @Deprecated(since = "2023.03")
    public void createPostNotification(final Post post) {
        final List<User> users = post.getGroup().getProfiles().stream().map(Profile::getUser).collect(toList());
        final String url = format("%s/discussions/%s", appUrl, post.getId());
        final String message = format("Une nouvelle discussion a été publiée sur le site implic'action : <a href=\"%s\">%s</a>.", url, post.getName());
        final String title = "[Implic'action] Une nouvelle discussion a été publiée";
        final EmailObject emailObject = createNotification(users, message, title);

        if (emailObject != null) {
            mailService.sendMail(DEFAULT_TEMPLATE, emailObject);
        }
    }

    @Deprecated(since = "2023.03")
    private NotificationEmailDto createNotification(final List<User> users, final String message, final String title) {
        final List<String> recipients = emptyStreamIfNull(users)
                .map(User::getEmail)
                .filter(Objects::nonNull)
                .collect(toList());

        if (recipients.isEmpty()) {
            return null;
        }

        return NotificationEmailDto.builder()
                .recipients(recipients)
                .body(message)
                .subject(title)
                .build();
    }
}
