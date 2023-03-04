package com.dynonuggets.refonteimplicaction.core.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ValidationExceptionResponse extends ExceptionResponse {

    /**
     * Map contenant le nom des champs en erreur de validation → raison de la non-validation
     */
    private final Map<String, String> errors;

    @Builder(builderMethodName = "ValidationExceptionResponseBuilder")
    public ValidationExceptionResponse(final String errorMessage, final int errorCode, final LocalDateTime timestamp, final Map<String, String> errors) {
        super(errorMessage, errorCode, timestamp);
        this.errors = errors;
    }
}
