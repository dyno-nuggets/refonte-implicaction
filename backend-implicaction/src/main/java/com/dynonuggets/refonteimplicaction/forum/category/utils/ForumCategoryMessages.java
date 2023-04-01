package com.dynonuggets.refonteimplicaction.forum.category.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ForumCategoryMessages {
    public static final String FORUM_CATEGORY_NOT_FOUND_MESSAGE = "La catégorie {id: %s} est introuvable";
    public static final String DELETE_CATEGORY_WITH_CHILD_MESSAGE = "Impossible de supprimer la catégorie {id: %s}. Il existe encore des catégories enfants";
    public static final String DELETE_CATEGORY_WITH_TOPIC_MESSAGE = "Impossible de supprimer la catégorie {id: %s}. Il existe encore des topics";
    // TODO: à améliorer
    public static final String CANNOT_ASSIGN_CHILD_CATEGORY_TO_ITSELF_MESSAGE = "Le parent d'une catégorie ne peut être lui même ou l'un de ses enfants";
}
