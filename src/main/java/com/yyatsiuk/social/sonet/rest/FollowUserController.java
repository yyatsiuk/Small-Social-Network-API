package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.FollowsDTO;
import com.yyatsiuk.social.sonet.dto.response.FollowUserResponse;
import com.yyatsiuk.social.sonet.dto.response.FollowerResponse;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import com.yyatsiuk.social.sonet.service.FollowUserService;
import com.yyatsiuk.social.sonet.utils.FollowUserMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class FollowUserController {

    private FollowUserService followService;
    private ModelMapper mapper;
    private FollowUserMapper customMapper;

    @Autowired
    public FollowUserController(
            FollowUserService followService,
            ModelMapper mapper,
            FollowUserMapper customMapper
    ) {
        this.followService = followService;
        this.mapper = mapper;
        this.customMapper = customMapper;
    }

    @GetMapping("/followings/{followerId}")
    public ResponseEntity<List<FollowerResponse>>
    getFollowingsByFollowerId(@PathVariable("followerId") Long followerId) {

        List<User> followings = followService.getFollowings(followerId);
        List<FollowerResponse> response = mapper.map(followings,
                new TypeToken<List<FollowerResponse>>() {
                }.getType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/followers/{followingId}")
    public ResponseEntity<List<FollowerResponse>>
    getFollowersByFollowingId(@PathVariable("followingId") Long followingId) {

        List<User> followers = followService.getFollowers(followingId);
        List<FollowerResponse> response = mapper.map(followers,
                new TypeToken<List<FollowerResponse>>() {
                }.getType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/follow")
    public ResponseEntity<FollowUserResponse> follow(@RequestBody FollowsDTO followsDTO) {
        UserFollow followResp = followService.follow(followsDTO);
        return new ResponseEntity<>(customMapper.toDTO(followResp), HttpStatus.CREATED);

    }

    @DeleteMapping("/unfollow")
    public ResponseEntity unfollow(@Valid @RequestBody FollowsDTO followsDTO) {
        followService.unfollow(followsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/follow")
    public ResponseEntity checkIfFollowed(
            @RequestParam Long followerId, @RequestParam Long followingId) {

        boolean isFollowed = followService.checkIfFollowed(followerId, followingId);
        if (isFollowed) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}