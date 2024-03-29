package com.dynonuggets.refonteimplicaction.auth.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthMessages {
    public static final String USER_SIGNUP_SUCCESS_MESSAGE = "L'utilisateur a été enregistré avec succès";
    public static final String REFRESH_TOKEN_DELETED_SUCCESSFULLY_MESSAGE = "Le refresh token a bien été supprimé";
    public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Le nom d'utilisateur {%s} est déjà associé à un compte utilisateur";
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "L'email {%s} est déjà associé à un compte utilisateur";
    public static final String ACTIVATION_KEY_NOT_FOUND_MESSAGE = "Clé d'activation introuvable : {%s}";
    public static final String REFRESH_TOKEN_EXPIRED_MESSAGE = "La session a expiré, veuillez vous identifier";
    public static final String USER_MAIL_IS_ALREADY_VERIFIED_MESSAGE = "L'adresse email de l'utilisateur {%s} est déjà vérifiée";
    public static final String USER_IS_NOT_ACTIVATED_MESSAGE = "Votre compte utilisateur doit d'abord être validé par un administrateur avant de pouvoir vous identifier";
    public static final String PUBLIC_KEY_ERROR_MESSAGE = "Une exception s'est produite lors de la récupération de la clé publique";
    public static final String OPERATION_NOT_PERMITTED_MESSAGE = "L'opération n'est pas autorisée";
}
