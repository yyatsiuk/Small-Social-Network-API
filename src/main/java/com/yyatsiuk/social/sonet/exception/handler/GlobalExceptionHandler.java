package com.yyatsiuk.social.sonet.exception.handler;

import com.yyatsiuk.social.sonet.dto.ErrorMessage;
import com.yyatsiuk.social.sonet.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        logger.warn(ex.getMessage(), ex);
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class, GoogleAuthenticationException.class, FacebookAuthenticationException.class, InvalidAuthorizationException.class})
    public ResponseEntity<Object> handleAuthenticationException(
            RuntimeException ex, WebRequest request) {
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        logger.warn("Problem with Authentication: {0}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(
            Exception ex, WebRequest request) {
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler({ProfileNotActiveException.class})
    protected ResponseEntity<Object> handleConflict(
            IllegalStateException ex, WebRequest request) {
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        logger.warn("Try to login with non activated account {0}",ex.getMessage());
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({FieldMissMachException.class, NicknameAlreadyExistException.class, EmailAlreadyExistException.class})
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
        ErrorMessage msg = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(
                msg, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
