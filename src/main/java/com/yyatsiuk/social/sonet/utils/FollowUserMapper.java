package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.response.FollowUserResponse;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import org.springframework.stereotype.Component;

@Component
public class FollowUserMapper implements CustomModelMapper<UserFollow, FollowUserResponse> {

    @Override
    public FollowUserResponse toDTO(UserFollow entity) {
        FollowUserResponse followUserResponse = new FollowUserResponse();
        followUserResponse.setCreationTime(entity.getCreationTime());
        followUserResponse.setUpdateTime(entity.getUpdateTime());
        followUserResponse.setFollowerId(entity.getFollowerId());
        followUserResponse.setFollowingId(entity.getFollowingId());

        return followUserResponse;
    }
}
