package com.yyatsiuk.social.sonet.exception;

import java.text.MessageFormat;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSendingException(Throwable cause) {
        super(cause);
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
