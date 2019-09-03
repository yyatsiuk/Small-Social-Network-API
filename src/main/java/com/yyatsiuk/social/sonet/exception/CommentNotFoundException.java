package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String message, Object... args){
        super(getFormattedString(message, args));

    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
