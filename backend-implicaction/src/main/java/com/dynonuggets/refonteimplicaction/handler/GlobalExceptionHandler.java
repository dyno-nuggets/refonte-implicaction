package com.dynonuggets.refonteimplicaction.handler;

import com.dynonuggets.refonteimplicaction.dto.ExceptionResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedException.class, AuthenticationException.class})
    public ResponseEntity<ExceptionResponse> unauthorizedException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(value = {NotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionResponse> userNotFoundException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(NOT_FOUND)
                .body(response);
    }
}
