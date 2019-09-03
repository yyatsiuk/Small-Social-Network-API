package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.model.FollowsDTO;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import com.yyatsiuk.social.sonet.repository.FollowRepo;
import com.yyatsiuk.social.sonet.service.FollowUserService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowUserServiceImpl implements FollowUserService {

    private final FollowRepo followRepo;
    private final ModelMapper mapper;
    private final UserService userService;

    public FollowUserServiceImpl(
            FollowRepo followRepo,
            ModelMapper mapper,
            UserService userService
    ) {
        this.followRepo = followRepo;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public UserFollow follow(FollowsDTO followsDTO) {
        UserFollow follow = new UserFollow();
        follow.setCreationTime(LocalDateTime.now());
        follow.setUpdateTime(LocalDateTime.now());
        follow.setFollowerId(followsDTO.getFollowerId());
        follow.setFollowingId(followsDTO.getFollowingId());
        follow.setFollower(userService.getUserById(followsDTO.getFollowerId()));
        follow.setFollowing(userService.getUserById(followsDTO.getFollowingId()));

        return followRepo.save(follow);
    }

    @Override
    public void unfollow(FollowsDTO request) {
        followRepo.delete(mapper.map(request, UserFollow.class));
    }

    @Override
    public List<User> getFollowings(Long followerId) {
        return followRepo.findByFollowerId(followerId)
                .stream()
                .map(UserFollow::getFollowing)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getFollowers(Long followingId) {
        return followRepo.findByFollowingId(followingId)
                .stream()
                .map(UserFollow::getFollower)
                .collect(Collectors.toList());
    }

    public boolean checkIfFollowed(Long followerId, Long followingId) {
       return followRepo.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}
