package com.dynonuggets.refonteimplicaction.core.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    // Le message que l'on veut afficher à l'utiliseur (front)
    protected String errorMessage;
    // Le code du statut HTTP (ex: 401, 404 ...)
    protected int errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{" +
                "\"errorMessage\": \"" + errorMessage + "\"" +
                ", \"errorCode\": \"" + errorCode + "\"" +
                ", \"timestamp\": \"" + timestamp + "\"" +
                '}';
    }
}
