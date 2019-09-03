package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.FollowToGroupRequest;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FollowGroupService {

    FollowGroup follow(FollowToGroupRequest followRequest);

    void unfollow(Long userId, Long groupId);

    Page<FollowGroup> getFollowsByGroupId(Long groupId, Pageable pageable);

    Page<FollowGroup> getFollowsByFollowerId(Long userId, Pageable pageable);

    boolean isFollowed(Long followerId, Long groupId);

}
