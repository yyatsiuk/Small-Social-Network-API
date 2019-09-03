package com.yyatsiuk.social.sonet.dto.model;

import lombok.Data;

@Data
public class UserWithLocationDTO implements DTO {

    private Long id;

    private double distance;

    private String email;

    private String firstName;

    private String lastName;

    private String nickname;

    private ImageDTO avatar;

}
