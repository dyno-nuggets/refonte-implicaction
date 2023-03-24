package com.dynonuggets.refonteimplicaction.community.relation.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationMessages.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum RelationErrorResult implements BaseErrorResult {

    // Relation
    RELATION_NOT_FOUND(NOT_FOUND, RELATION_NOT_FOUND_MESSAGE),
    SENDER_EQUALS_RECEIVER(CONFLICT, SENDER_IS_EQUALS_RECEIVER_MESSAGE),
    RELATION_ALREADY_EXISTS(CONFLICT, RELATION_ALREADY_EXISTS_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
