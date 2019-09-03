package com.yyatsiuk.social.sonet.exception;


public class FacebookAuthenticationException extends RuntimeException {

    public FacebookAuthenticationException(String message) {
        super(message);
    }

    public FacebookAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
