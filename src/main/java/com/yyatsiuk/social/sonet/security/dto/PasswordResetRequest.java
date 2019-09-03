package com.yyatsiuk.social.sonet.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String token;
}
