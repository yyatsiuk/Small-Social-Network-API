package com.yyatsiuk.social.sonet.security.jwt;

import com.yyatsiuk.social.sonet.filter.UserOnlineFilter;
import com.yyatsiuk.social.sonet.tracking.UserOnlineTracker;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private UserOnlineTracker userOnlineTracker;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, UserOnlineTracker userOnlineTracker) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userOnlineTracker = userOnlineTracker;
    }

    @Override
    public void configure(HttpSecurity builder) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        UserOnlineFilter userOnlineFilter = new UserOnlineFilter(userOnlineTracker);
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(userOnlineFilter,UsernamePasswordAuthenticationFilter.class);
    }


}
