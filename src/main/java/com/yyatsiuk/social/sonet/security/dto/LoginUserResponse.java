package com.yyatsiuk.social.sonet.security.dto;

import com.yyatsiuk.social.sonet.model.Image;
import lombok.Data;

@Data
public class LoginUserResponse {

    private Long id;

    private String email;

    private String nickname;

    private String token;

    private String firstName;

    private String lastName;

    private Image avatar;

    private Image background;

}
