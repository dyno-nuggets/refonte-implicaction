package com.dynonuggets.refonteimplicaction.forum.category.error;

import com.dynonuggets.refonteimplicaction.forum.commons.error.ForumErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.forum.category.utils.ForumCategoryMessages.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ForumCategoryErrorResult implements ForumErrorResult {
    FORUM_CATEGORY_NOT_FOUND(NOT_FOUND, FORUM_CATEGORY_NOT_FOUND_MESSAGE),
    DELETE_CATEGORY_WITH_CHILD(CONFLICT, DELETE_CATEGORY_WITH_CHILD_MESSAGE),
    DELETE_CATEGORY_WITH_TOPIC(CONFLICT, DELETE_CATEGORY_WITH_TOPIC_MESSAGE),
    CANNOT_ASSIGN_CHILD_CATEGORY_TO_ITSELF(CONFLICT, CANNOT_ASSIGN_CHILD_CATEGORY_TO_ITSELF_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
