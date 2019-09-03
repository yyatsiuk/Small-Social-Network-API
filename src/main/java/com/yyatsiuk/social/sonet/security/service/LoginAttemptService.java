package com.yyatsiuk.social.sonet.security.service;

public interface LoginAttemptService {

    void loginSucceeded(String remoteAddress);

    boolean isBlocked(String ip);

    void loginFailed(String key);

}
