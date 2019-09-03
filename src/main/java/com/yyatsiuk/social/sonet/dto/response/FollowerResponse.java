package com.yyatsiuk.social.sonet.dto.response;

import com.yyatsiuk.social.sonet.model.Image;
import lombok.Data;

@Data
public class FollowerResponse {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private Image avatar;

}