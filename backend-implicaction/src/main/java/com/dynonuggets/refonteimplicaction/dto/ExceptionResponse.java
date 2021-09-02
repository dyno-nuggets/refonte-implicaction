package com.dynonuggets.refonteimplicaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionResponse {

    // Le message que l'on veut afficher à l'utiliseur (front)
    private String errorMessage;
    // Le code du statut HTTP (ex: 401, 404 ...)
    private int errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{" +
                "\"errorMessage\": \"" + errorMessage + "\"" +
                ", \"errorCode\": \"" + errorCode + "\"" +
                ", \"timestamp\": \"" + timestamp + "\"" +
                '}';
    }
}
