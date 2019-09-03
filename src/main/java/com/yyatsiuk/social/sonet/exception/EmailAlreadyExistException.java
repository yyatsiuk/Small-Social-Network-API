package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class EmailAlreadyExistException extends IllegalArgumentException {

    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
