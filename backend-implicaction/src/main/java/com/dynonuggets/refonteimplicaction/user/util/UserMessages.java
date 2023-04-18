package com.dynonuggets.refonteimplicaction.user.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMessages {
    public static final String USERNAME_NOT_FOUND_MESSAGE = "Utilisateur introuvable : nom d'utilisateur ou mot de passe incorrect";
    public static final String USER_ID_NOT_FOUND_MESSAGE = "L'utilisateur d'id {%s} n'existe pas";
}
