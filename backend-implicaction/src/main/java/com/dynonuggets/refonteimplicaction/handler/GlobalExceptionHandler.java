package com.dynonuggets.refonteimplicaction.handler;

import com.dynonuggets.refonteimplicaction.dto.ExceptionResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BAD_CREDENTIAL_MSG = "Nom d'utilisateur ou mot de passe incorrect.";
    private static final String USER_DISABLED_MSG = "Votre compte n'a pas encore été activé.";

    @ExceptionHandler(value = {UnauthorizedException.class, AuthenticationException.class})
    public ResponseEntity<ExceptionResponse> unauthorizedException(Exception ex) {
        return generateResponse(ex.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionResponse> userNotFoundException(Exception ex) {
        return generateResponse(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsException() {
        return generateResponse(BAD_CREDENTIAL_MSG, UNAUTHORIZED);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException() {
        return generateResponse(USER_DISABLED_MSG, UNAUTHORIZED);
    }

    private ResponseEntity<ExceptionResponse> generateResponse(final String message, final HttpStatus httpStatus) {
        ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(message)
                .errorCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
