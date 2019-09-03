package com.yyatsiuk.social.sonet.dto.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO implements DTO {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String nickname;

    private String city;

    private String planet;

    private String country;

    private ImageDTO avatar;

    private ImageDTO background;

    private Boolean isOnline;

    private LocalDateTime lastActivity;

}
