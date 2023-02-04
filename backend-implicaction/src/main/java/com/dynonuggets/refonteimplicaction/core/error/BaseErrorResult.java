package com.dynonuggets.refonteimplicaction.core.error;

import org.springframework.http.HttpStatus;

public interface BaseErrorResult {

    HttpStatus getStatus();

    String getMessage();

}
