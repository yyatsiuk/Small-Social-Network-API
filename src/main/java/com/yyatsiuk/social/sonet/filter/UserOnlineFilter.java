package com.yyatsiuk.social.sonet.filter;

import com.yyatsiuk.social.sonet.security.dto.JwtUser;
import com.yyatsiuk.social.sonet.tracking.UserOnlineTracker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UserOnlineFilter extends GenericFilterBean {

    private UserOnlineTracker userOnlineTracker;


    public UserOnlineFilter(UserOnlineTracker userOnlineTracker) {
        this.userOnlineTracker = userOnlineTracker;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if(authentication != null) {
            JwtUser user =  (JwtUser)authentication.getPrincipal();
                Long userId = user.getId();
                userOnlineTracker.notifyForLastActivity(userId);
        }

        filterChain.doFilter(req, res);

    }

}
