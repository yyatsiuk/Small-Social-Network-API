package com.yyatsiuk.social.sonet.security.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleUserInfo {

    private String email;

    private String id;

    private String idToken;

    private String lastName;

    private String name;

    private String photoUrl;

}
