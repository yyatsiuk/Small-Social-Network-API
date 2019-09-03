package com.yyatsiuk.social.sonet.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendConfirm(String email,String confirmLink) throws MessagingException;

    void sendBan(String email) throws MessagingException;

    void sendRestorePassword(String email,String confirmLink) throws MessagingException;

    void sendRestoreSuccessful(String email) throws MessagingException;
}
