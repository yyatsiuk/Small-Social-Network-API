package com.yyatsiuk.social.sonet.security;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EventSendingAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("NullableProblems")
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        if (exception instanceof BadCredentialsException) {
            String name = request.getParameter("username");
            String password = request.getParameter("password");
            Authentication auth =
                    new UsernamePasswordAuthenticationToken(name, password);
            eventPublisher.publishEvent(
                    new AuthenticationFailureBadCredentialsEvent(auth, exception));
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}