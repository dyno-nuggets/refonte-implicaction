package com.dynonuggets.refonteimplicaction.community.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CommunityMessages {

    // PROFILE
    public static final String PROFILE_NOT_FOUND_MESSAGE = "Le profil {%s} est introuvable";

    // RELATION
    public static final String RELATION_NOT_FOUND_MESSAGE = "La relation est introuvable";
    public static final String SENDER_IS_EQUALS_RECEIVER_MESSAGE = "Une relation concerne nécessairement deux utilisateurs distincts";
    public static final String RELATION_ALREADY_EXISTS_MESSAGE = "Les utilisateurs sont déjà en relation";
}
