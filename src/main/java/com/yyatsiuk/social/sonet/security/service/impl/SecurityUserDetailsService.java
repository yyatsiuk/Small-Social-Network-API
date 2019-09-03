package com.yyatsiuk.social.sonet.security.service.impl;

import com.yyatsiuk.social.sonet.security.dto.SecuredUserDTO;
import com.yyatsiuk.social.sonet.security.jwt.JwtUserFactory;
import com.yyatsiuk.social.sonet.security.service.LoginAttemptService;
import com.yyatsiuk.social.sonet.security.service.UserSecurityService;
import com.yyatsiuk.social.sonet.security.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);
    private final UserSecurityService userSecurityService;
    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;
    private final SecurityUtils securityUtils;

    public SecurityUserDetailsService(
            @Lazy UserSecurityService userSecurityService,
            @Lazy HttpServletRequest request,
            @Lazy LoginAttemptService loginAttemptService,
            @Lazy SecurityUtils securityUtils
    ) {
        this.userSecurityService = userSecurityService;
        this.request = request;
        this.loginAttemptService = loginAttemptService;
        this.securityUtils = securityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        String ip = securityUtils.getClientIP(request);
        if (loginAttemptService.isBlocked(ip)) {
            throw new AccessDeniedException("We are sorry but we were forced to block " +
                    "your ip address for 1 minutes, you made too many incorrect login attempts");
        }

        SecuredUserDTO userDTO = userSecurityService.findByEmail(email);
        return JwtUserFactory.userToJwtUser(userDTO);
    }
}


