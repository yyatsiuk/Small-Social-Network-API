package com.yyatsiuk.social.sonet.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FollowGroupPageableResp {

    private List<FollowerResponse> followerResponses;

    private int numberOdPages;
}
