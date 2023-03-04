package com.dynonuggets.refonteimplicaction.auth.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthMessages {
    public static final String USER_SIGNUP_SUCCESS_MESSAGE = "L'utilisateur a été enregistré avec succès";
    public static final String REFRESH_TOKEN_DELETED_SUCESSFULLY_MESSAGE = "Le refresh token a bien été supprimé";
    public static final String USER_NOT_FOUND_MESSAGE = "Utilisateur introuvable : nom d'utilisateur ou mot de passe incorrect";
    public static final String USER_ID_NOT_FOUND_MESSAGE = "L'utilisateur d'id {%s} n'existe pas";
    public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Le nom d'utilisateur {%s} est déjà associé à un compte utilisateur";
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "L'email {%s} est déjà associé à un compte utilisateur";
    public static final String ACTIVATION_KEY_NOT_FOUND_MESSAGE = "Clé d'activation introuvable : {%s}";
    public static final String REFRESH_TOKEN_EXPIRED_MESSAGE = "Votre session a expiré, veuillez vous identifier";
    public static final String USER_ALREADY_ACTIVATED_MESSAGE = "L'utilisateur associé à la clé d'activation {%s} est déjà activé";
    public static final String USER_IS_NOT_ACTIVATED_MESSAGE = "Votre compte utilisateur doit d'abord être validé par un utilisateur";
    public static final String PUBLIC_KEY_ERROR_MESSAGE = "Une exception s'est produite lors de la récupération de la clé publique";
    public static final String USERNAME_NOT_FOUND_MESSAGE = "L'utilisateur {%s} n'existe pas";
}
