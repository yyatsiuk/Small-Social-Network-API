package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class ProfileNotActiveException extends IllegalStateException {

    public ProfileNotActiveException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public ProfileNotActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileNotActiveException(Throwable cause) {
        super(cause);
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);
    }
}
