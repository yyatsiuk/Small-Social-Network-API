package com.yyatsiuk.social.sonet.dto.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FollowsDTO implements DTO {

    private Long followerId;

    private Long followingId;

}
