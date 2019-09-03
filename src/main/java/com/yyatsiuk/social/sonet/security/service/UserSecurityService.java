package com.yyatsiuk.social.sonet.security.service;

import com.yyatsiuk.social.sonet.exception.EmailSendingException;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.security.dto.RegisterUserRequest;
import com.yyatsiuk.social.sonet.security.dto.SecuredUserDTO;

public interface UserSecurityService {

    User register(RegisterUserRequest requestUser);

    SecuredUserDTO findByEmail(String username);

    SecuredUserDTO findById(Long id);

    boolean verifyEmailToken(String token);

    boolean resetPasswordRequest(String email) throws EmailSendingException;

    boolean resetPassword(String token, String password) throws EmailSendingException;

    boolean isActive(String email);
}
