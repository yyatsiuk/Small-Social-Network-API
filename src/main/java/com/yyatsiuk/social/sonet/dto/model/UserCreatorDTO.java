package com.yyatsiuk.social.sonet.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreatorDTO implements DTO {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private ImageDTO avatar;

    private ImageDTO background;
}
