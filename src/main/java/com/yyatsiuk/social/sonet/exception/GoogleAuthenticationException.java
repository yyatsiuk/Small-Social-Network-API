package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class GoogleAuthenticationException extends RuntimeException {

    public GoogleAuthenticationException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public GoogleAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);
    }
}
