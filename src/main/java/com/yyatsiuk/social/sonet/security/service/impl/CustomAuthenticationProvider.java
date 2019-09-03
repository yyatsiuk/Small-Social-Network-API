package com.yyatsiuk.social.sonet.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SecurityUserDetailsService userDetailsService;

    @Autowired
    public CustomAuthenticationProvider(SecurityUserDetailsService userService) {
        this.userDetailsService = userService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        if (auth == null) {
            throw new BadCredentialsException("Cannot authenticate an empty authentication.");
        }

        String username = auth.getName();

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        if(auth.getCredentials() != null) {
           String password = auth.getCredentials()
                    .toString();


            String hashedPassword = userDetails.getPassword();

            if (hashedPassword == null) {
                return null;
            } else if (BCrypt.checkpw(password, hashedPassword)) {
                return new UsernamePasswordAuthenticationToken
                        (userDetails, password, Collections.emptyList());
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        }else {
            return new UsernamePasswordAuthenticationToken
                    (userDetails, null, Collections.emptyList());
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsernamePasswordAuthenticationToken.class);
    }

}
