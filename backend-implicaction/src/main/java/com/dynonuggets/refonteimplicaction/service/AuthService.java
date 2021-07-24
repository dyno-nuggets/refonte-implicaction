package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicitActionException;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.util.Constants.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Transactional
    public void signup(ReqisterRequestDto reqisterRequest) throws ImplicitActionException {
        final String activationKey = generateActivationKey();

        User user = User.builder()
                .login(reqisterRequest.getLogin())
                .email(reqisterRequest.getEmail())
                .password(passwordEncoder.encode(reqisterRequest.getPassword()))
                .registered(Instant.now())
                .activationKey(activationKey)
                .build();
        userRepository.save(user);

        NotificationEmailDto notificationEmail = NotificationEmailDto.builder()
                .subject("Please activate your account")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                        + ACTIVATION_EMAIL + "/" + activationKey)
                .build();
        mailService.sendMail(notificationEmail);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }
}
