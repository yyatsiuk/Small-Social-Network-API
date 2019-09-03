package com.yyatsiuk.social.sonet.security.dto;

import com.yyatsiuk.social.sonet.model.Status;
import lombok.Data;

@Data
public class RegisterUserResponse {

    private Long id;

    private String email;

    private String nickName;

    private Status status;

}
