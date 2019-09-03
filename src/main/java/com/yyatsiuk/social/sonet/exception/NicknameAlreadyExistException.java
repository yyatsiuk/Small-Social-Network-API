package com.yyatsiuk.social.sonet.exception;


import java.text.MessageFormat;

public class NicknameAlreadyExistException extends IllegalArgumentException {

    public NicknameAlreadyExistException() {
        super();
    }

    public NicknameAlreadyExistException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    private static String getFormattedString(String message, Object... args){
        return MessageFormat.format(message, args);

    }
}
