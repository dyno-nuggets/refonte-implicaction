package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.dto.ReqisterRequestDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicitActionException;
import com.dynonuggets.refonteimplicaction.model.Signup;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.SignUpRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.dynonuggets.refonteimplicaction.util.Constants.ACTIVATION_ENDPOINT;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SignUpRepository signUpRepository;
    private final MailService mailService;

    /**
     * Enregistre un utilisateur en base de données et lui envoie un mail d'activation
     * crée également une entrée dans la table wp_signups
     * @param reqisterRequest données d'identification de l'utilisateur
     * @throws ImplicitActionException si l'envoi du mail échoue
     * TODO: notifier lors de l'existence d'un utilisateur ayant le même mail ou login
     */
    @Transactional
    public void signupAndSendConfirmation(ReqisterRequestDto reqisterRequest) throws ImplicitActionException {
        final String activationKey = generateActivationKey();

        User user = User.builder()
                .login(reqisterRequest.getLogin())
                .email(reqisterRequest.getEmail())
                .password(passwordEncoder.encode(reqisterRequest.getPassword()))
                .registered(Instant.now())
                .activationKey(activationKey)
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .userLogin(reqisterRequest.getLogin())
                .userEmail(reqisterRequest.getEmail())
                .registered(user.getRegistered())
                .active(false)
                .activationKey(activationKey)
                .build();
        signUpRepository.save(signup);

        NotificationEmailDto notificationEmail = NotificationEmailDto.builder()
                .subject("Please activate your account")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Implicaction, please click on the below url to activate your account : "
                        + ACTIVATION_ENDPOINT + "/" + activationKey)
                .build();
        mailService.sendMail(notificationEmail);
    }

    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }


    /**
     * Vérifie existence et l'activation d'une clé d'activation et l'active si elle ne l'est pas déjà
     * @throws ImplicitActionException
     * <ul>
     *     <li>Si la clé n'existe pas</li>
     *     <li>Si la clé est déjà activée</li>
     * </ul>
     */
    public void verifyAccount(String activationKey) throws ImplicitActionException {
        final Optional<Signup> signupToActivate = signUpRepository.findByActivationKey(activationKey);
        Signup signup = signupToActivate.orElseThrow(() -> new ImplicitActionException("Activation Key Not Found: " + activationKey));
        if (Boolean.TRUE.equals(signup.getActive())) {
            throw new ImplicitActionException("Account With Associated Activation Key Already Activated - " + activationKey);
        }
        activateSignup(signup);
    }

    private void activateSignup(Signup signup) {
        signup.setActivated(Instant.now());
        signup.setActive(true);
        signUpRepository.save(signup);
    }
}
