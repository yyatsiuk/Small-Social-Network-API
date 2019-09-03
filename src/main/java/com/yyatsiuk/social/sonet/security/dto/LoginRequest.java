package com.yyatsiuk.social.sonet.security.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = LoginUserRequest.class)
public interface LoginRequest {

    String getEmail();

    String getPassword();

    void setEmail(String email);

    void setPassword(String password);
}
