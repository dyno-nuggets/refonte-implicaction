package com.dynonuggets.refonteimplicaction.community.profile.error;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_NOT_FOUND_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ProfileErrorResult implements BaseErrorResult {

    PROFILE_NOT_FOUND(NOT_FOUND, PROFILE_NOT_FOUND_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
