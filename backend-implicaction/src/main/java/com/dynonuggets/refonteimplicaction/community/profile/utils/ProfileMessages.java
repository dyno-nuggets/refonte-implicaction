package com.dynonuggets.refonteimplicaction.community.profile.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProfileMessages {
    public static final String PROFILE_NOT_FOUND_MESSAGE = "Le profil {%s} est introuvable, l'utilisateur n'existe pas ou n'est pas activé";
    public static final String PROFILE_ALREADY_EXISTS_MESSAGE = "Impossible de créer le profil : Le profil {%s} exite déjà";
    public static final String PROFILE_CREATED_SUCCESSFULLY_MESSAGE = "Le profile utilisateur {%s} a été créé avec succès";
}
