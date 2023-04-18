package com.dynonuggets.refonteimplicaction.core.dto;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    // Le message que l'on veut afficher à l'utilisateur (front)
    protected String errorMessage;
    // Le code du statut HTTP (ex: 401, 404 ...)
    protected int errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected LocalDateTime timestamp;

    public static ExceptionResponse from(final BaseErrorResult errorResult) {
        return ExceptionResponse.builder()
                .errorMessage(errorResult.getMessage())
                .errorCode(errorResult.getStatus().value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ExceptionResponse from(final Exception exception, final HttpStatus httpStatus) {
        return ExceptionResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "\"errorMessage\": \"" + errorMessage + "\"" +
                ", \"errorCode\": \"" + errorCode + "\"" +
                ", \"timestamp\": \"" + timestamp + "\"" +
                '}';
    }
}
