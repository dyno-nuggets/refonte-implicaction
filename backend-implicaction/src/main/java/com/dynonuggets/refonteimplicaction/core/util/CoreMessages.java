package com.dynonuggets.refonteimplicaction.core.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CoreMessages {
    public static final String ERROR_FIELD_VALIDATION_MESSAGE = "Erreur de validation des champs";
    public static final String BAD_CREDENTIAL_MESSAGE = "Utilisateur introuvable : nom d'utilisateur ou mot de passe incorrect";
    public static final String USERNAME_NOT_FOUND_MESSAGE = "L'utilisateur {%s} n'existe pas";
    public static final String USER_ID_NOT_FOUND_MESSAGE = "L'utilisateur d'id {%s} n'existe pas";
}
