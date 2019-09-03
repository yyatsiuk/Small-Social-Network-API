package com.yyatsiuk.social.sonet.exception;

import java.text.MessageFormat;

public class ChannelAlreadyExistException extends RuntimeException {

    public ChannelAlreadyExistException() {
        super();
    }
    public ChannelAlreadyExistException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
