package com.yyatsiuk.social.sonet.exception;

import java.text.MessageFormat;

public class FieldMissMachException extends IllegalArgumentException {

    public FieldMissMachException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public FieldMissMachException(String message, Throwable cause) {
        super(message, cause);
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
