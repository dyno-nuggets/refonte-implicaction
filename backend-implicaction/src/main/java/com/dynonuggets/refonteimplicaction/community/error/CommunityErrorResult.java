package com.dynonuggets.refonteimplicaction.community.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.community.utils.CommunityMessages.*;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CommunityErrorResult implements BaseErrorResult {

    // Profile
    PROFILE_NOT_FOUND(NOT_FOUND, PROFILE_NOT_FOUND_MESSAGE),

    // Relation
    RELATION_NOT_FOUND(NOT_FOUND, RELATION_NOT_FOUND_MESSAGE),
    USER_UNAUTHORIZED_TO_CONFIRM_RELATION(UNAUTHORIZED, USER_UNAUTHORIZED_TO_CONFIRM_RELATION_MESSAGE),
    SENDER_EQUALS_RECEIVER(CONFLICT, SENDER_IS_EQUALS_RECEIVER_MESSAGE),
    RELATION_ALREADY_EXISTS(CONFLICT, RELATION_ALREADY_EXISTS_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
