package com.yyatsiuk.social.sonet.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginRequest implements LoginRequest {

    private String email;

    private String password;

}
