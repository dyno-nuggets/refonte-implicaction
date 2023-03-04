package com.dynonuggets.refonteimplicaction.core.handler;

import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.core.rest.dto.ExceptionResponse;
import com.dynonuggets.refonteimplicaction.core.rest.dto.ValidationExceptionResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.dynonuggets.refonteimplicaction.core.rest.dto.ExceptionResponse.from;
import static com.dynonuggets.refonteimplicaction.core.util.CoreMessages.BAD_CREDENTIAL_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.util.CoreMessages.ERROR_FIELD_VALIDATION_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.util.Message.USER_DISABLED_MESSAGE;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ImplicactionException.class)
    ResponseEntity<ExceptionResponse> implicactionException(final ImplicactionException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ex.getErrorResult().getStatus().value())
                .body(from(ex, ex.getErrorResult().getStatus()));
    }

    @ExceptionHandler(value = {UnauthorizedException.class, AuthenticationException.class})
    public ResponseEntity<ExceptionResponse> unauthorizedException(final Exception ex) {
        return ResponseEntity.status(UNAUTHORIZED).body(from(ex, UNAUTHORIZED));
    }

    @ExceptionHandler(value = {NotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionResponse> userNotFoundException(final Exception ex) {
        return ResponseEntity.status(NOT_FOUND).body(from(ex, NOT_FOUND));
    }

    // L'exception en amont n'est pas catchable, on est obligé de récupérer les BadCredentialsException
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsException() {
        return generateResponse(BAD_CREDENTIAL_MESSAGE, UNAUTHORIZED);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException() {
        return generateResponse(USER_DISABLED_MESSAGE, UNAUTHORIZED);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<ExceptionResponse> illegalArgumentException(final Exception ex) {
        return generateResponse(ex.getMessage(), BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> generateResponse(final String message, final HttpStatus httpStatus) {
        log.error(message);
        final ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(message)
                .errorCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException ex,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        final ValidationExceptionResponse body = ValidationExceptionResponse.ValidationExceptionResponseBuilder()
                .errorMessage(ERROR_FIELD_VALIDATION_MESSAGE)
                .errorCode(status.value())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();


        return ResponseEntity
                .status(status.value())
                .body(body);
    }
}
