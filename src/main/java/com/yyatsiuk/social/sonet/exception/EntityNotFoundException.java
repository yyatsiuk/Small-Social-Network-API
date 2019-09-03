package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    private static String getFormattedString(String message, Object... args){
       return MessageFormat.format(message, args);

    }
}
