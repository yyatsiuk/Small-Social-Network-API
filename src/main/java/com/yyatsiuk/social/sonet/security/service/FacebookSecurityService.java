package com.yyatsiuk.social.sonet.security.service;

import com.yyatsiuk.social.sonet.security.dto.FacebookAccessTokenRequest;
import com.yyatsiuk.social.sonet.security.dto.LoginRequest;

public interface FacebookSecurityService {

    LoginRequest authenticateFacebookUser(FacebookAccessTokenRequest facebookRequest);
}
