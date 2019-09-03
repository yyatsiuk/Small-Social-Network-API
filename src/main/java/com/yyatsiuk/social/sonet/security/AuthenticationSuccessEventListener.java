package com.yyatsiuk.social.sonet.security;

import com.yyatsiuk.social.sonet.security.service.LoginAttemptService;
import com.yyatsiuk.social.sonet.security.util.SecurityUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;
    private final SecurityUtils securityUtils;

    public AuthenticationSuccessEventListener(
            LoginAttemptService loginAttemptService,
            HttpServletRequest request,
            SecurityUtils securityUtils) {
        this.loginAttemptService = loginAttemptService;
        this.request = request;
        this.securityUtils = securityUtils;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        loginAttemptService.loginSucceeded(securityUtils.getClientIP(request));
    }
}
