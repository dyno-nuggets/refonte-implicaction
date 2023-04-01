package com.dynonuggets.refonteimplicaction.forum.category.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ForumCategoryUris {
    public static final String CATEGORY_BASE_URI = "/api/forums/categories";
    public static final String GET_CATEGORY_URI = "/{categoryIds}";

    public static final String DELETE_CATEGORY_URI = "/{categoryId}";

    public static final String GET_TOPIC_FROM_CATEGORY_URI = "/{categoryId}/topics";
}
