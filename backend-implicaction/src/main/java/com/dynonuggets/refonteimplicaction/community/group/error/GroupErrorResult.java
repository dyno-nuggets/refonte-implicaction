package com.dynonuggets.refonteimplicaction.community.group.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupMessages.GROUP_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupMessages.USER_ALREADY_SUBSCRIBED_TO_GROUP_MESSAGE;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum GroupErrorResult implements BaseErrorResult {

    GROUP_NOT_FOUND(NOT_FOUND, GROUP_NOT_FOUND_MESSAGE),
    USER_ALREADY_SUBSCRIBED_TO_GROUP(CONFLICT, USER_ALREADY_SUBSCRIBED_TO_GROUP_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
