package com.dynonuggets.refonteimplicaction.forum.topic.error;

import com.dynonuggets.refonteimplicaction.forum.commons.error.ForumErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.forum.topic.utils.TopicMessages.TOPIC_NOT_FOUND_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum TopicErrorResult implements ForumErrorResult {

    TOPIC_NOT_FOUND(NOT_FOUND, TOPIC_NOT_FOUND_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
