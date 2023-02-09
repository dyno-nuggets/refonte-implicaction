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

import static com.dynonuggets.refonteimplicaction.utils.Message.BAD_CREDENTIAL_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.USER_DISABLED_MESSAGE;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


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
        return generateResponse(BAD_CREDENTIAL_MESSAGE, UNAUTHORIZED);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException() {
        return generateResponse(USER_DISABLED_MESSAGE, UNAUTHORIZED);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<ExceptionResponse> illegalArgumentException(Exception ex) {
        return generateResponse(ex.getMessage(), BAD_REQUEST);
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
