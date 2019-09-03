package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.FollowToGroupRequest;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroup;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroupPrimaryKey;
import com.yyatsiuk.social.sonet.repository.FollowGroupRepo;
import com.yyatsiuk.social.sonet.service.FollowGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FollowGroupServiceImpl implements FollowGroupService {

    private final FollowGroupRepo followGroupRepo;
    private final ModelMapper mapper;

    public FollowGroupServiceImpl(FollowGroupRepo followGroupRepo, ModelMapper mapper) {
        this.followGroupRepo = followGroupRepo;
        this.mapper = mapper;
    }

    @Override
    public FollowGroup follow(FollowToGroupRequest followRequest) {
        return followGroupRepo.save(mapper.map(followRequest, FollowGroup.class));
    }

    @Override
    public Page<FollowGroup> getFollowsByGroupId(Long groupId, Pageable pageable) {
        return followGroupRepo.findByGroupId(groupId, pageable);
    }

    @Override
    public Page<FollowGroup> getFollowsByFollowerId(Long userId, Pageable pageable) {
        return followGroupRepo.findByFollowerId(userId, pageable);
    }

    @Override
    public void unfollow(Long userId, Long groupId) {
        FollowGroupPrimaryKey key = new FollowGroupPrimaryKey();
        key.setFollowerId(userId);
        key.setGroupId(groupId);

        Optional<FollowGroup> followGroup =
                followGroupRepo.findById(key);
        followGroup.ifPresent(followGroupRepo::delete);
    }

    @Override
    public boolean isFollowed(Long followerId, Long groupId) {
        return followGroupRepo.existsByFollowerIdAndGroupId(followerId, groupId);
    }
}