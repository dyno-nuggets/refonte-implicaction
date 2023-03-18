package com.dynonuggets.refonteimplicaction.core.notification.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class NotificationMessages {
    public static final String MAIL_FEATURE_IS_DEACTIVATED_MESSAGE = "Le mail n'a pas été envoyé : la fonctionnalité est désactivée";
    public static final String MAIL_SUCCESS_LOG_MESSAGE = "Un email a été envoyé avec succès aux destinataires suivants {%s}";
    public static final String MAIL_ERROR_MESSAGE = "Une erreur s'est produite lors de l'envoi d'un mail";
}
