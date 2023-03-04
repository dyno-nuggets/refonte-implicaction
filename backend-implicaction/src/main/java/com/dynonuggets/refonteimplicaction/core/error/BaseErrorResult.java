package com.dynonuggets.refonteimplicaction.core.error;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public interface BaseErrorResult extends Serializable {

    HttpStatus getStatus();

    String getMessage();

}
