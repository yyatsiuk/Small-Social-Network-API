package com.yyatsiuk.social.sonet.security.service;

import com.yyatsiuk.social.sonet.security.dto.GoogleUserInfo;
import com.yyatsiuk.social.sonet.security.dto.LoginRequest;

public interface GoogleSecurityService {

    LoginRequest authenticateGoogleUser(GoogleUserInfo googleUser);
}
