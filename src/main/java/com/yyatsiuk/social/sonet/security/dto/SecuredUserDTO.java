package com.yyatsiuk.social.sonet.security.dto;

import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Role;
import com.yyatsiuk.social.sonet.model.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SecuredUserDTO {

    private Long id;

    private String email;

    private String token;

    private String nickname;

    private String firstName;

    private String lastName;

    private String password;

    private Image avatar;

    private Image background;

    private Status status = Status.NOT_ACTIVE;

    private LocalDateTime updateTime;

    private List<Role> roles;

    private String emailVerificationToken;

}
