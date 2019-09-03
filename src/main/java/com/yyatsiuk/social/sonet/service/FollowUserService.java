package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.model.FollowsDTO;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;

import java.util.List;

public interface FollowUserService {

    UserFollow follow(FollowsDTO followsDTO);

    void unfollow(FollowsDTO followsDTO);

    List<User> getFollowings(Long followerId);

    List<User> getFollowers(Long followingId);

    boolean checkIfFollowed(Long followerId, Long followingId);

}
