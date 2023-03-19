package com.dynonuggets.refonteimplicaction.core.user.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMessages {
    public static final String USERNAME_NOT_FOUND_MESSAGE = "L'utilisateur {%s} n'existe pas";
    public static final String USER_ID_NOT_FOUND_MESSAGE = "L'utilisateur d'id {%s} n'existe pas";
}
