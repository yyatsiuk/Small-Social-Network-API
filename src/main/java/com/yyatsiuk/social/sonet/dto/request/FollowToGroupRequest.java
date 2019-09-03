package com.yyatsiuk.social.sonet.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FollowToGroupRequest {

    @NotNull
    private Long followerId;

    @NotNull
    private Long groupId;

}
