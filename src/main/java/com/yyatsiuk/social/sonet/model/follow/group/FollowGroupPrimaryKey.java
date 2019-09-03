package com.yyatsiuk.social.sonet.model.follow.group;

import lombok.Data;

import java.io.Serializable;

@Data
public class FollowGroupPrimaryKey implements Serializable {

    private Long followerId;

    private Long groupId;
}
