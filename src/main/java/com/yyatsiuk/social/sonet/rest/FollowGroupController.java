package com.yyatsiuk.social.sonet.rest;


import com.yyatsiuk.social.sonet.dto.model.GroupDTO;
import com.yyatsiuk.social.sonet.dto.request.FollowToGroupRequest;
import com.yyatsiuk.social.sonet.dto.response.FollowGroupPageableResp;
import com.yyatsiuk.social.sonet.dto.response.FollowerResponse;
import com.yyatsiuk.social.sonet.dto.response.GroupPageableResponse;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroup;
import com.yyatsiuk.social.sonet.service.FollowGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/groups")
public class FollowGroupController {

    private FollowGroupService followGroupService;
    private ModelMapper modelMapper;


    @Autowired
    public FollowGroupController(
            FollowGroupService followGroupService,
            ModelMapper modelMapper

    ) {
        this.followGroupService = followGroupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/followers/{groupId}")
    public ResponseEntity<FollowGroupPageableResp>
    getFollowers(@PathVariable Long groupId, Pageable pageable) {

        Page<FollowGroup> followPage = followGroupService.getFollowsByGroupId(groupId, pageable);

        int numberOfPages = followPage.getTotalPages();
        List<FollowerResponse> followGroups = followPage.getContent()
                .stream()
                .map(FollowGroup::getFollower)
                .map(follower -> modelMapper.map(follower, FollowerResponse.class))
                .collect(Collectors.toList());

        FollowGroupPageableResp response = new FollowGroupPageableResp();
        response.setFollowerResponses(followGroups);
        response.setNumberOdPages(numberOfPages);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GroupPageableResponse>
    getGroups(@PathVariable Long userId, Pageable pageable) {

        Page<FollowGroup> groupPage = followGroupService.getFollowsByFollowerId(userId, pageable);

        int numberOfPages = groupPage.getTotalPages();
        List<GroupDTO> groupResponses = groupPage.getContent()
                .stream()
                .map(FollowGroup::getGroup)
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());

        GroupPageableResponse response = new GroupPageableResponse();
        response.setGroupDTOList(groupResponses);
        response.setNumberOfPages(numberOfPages);
        response.setAmountOfGroups(groupPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/member")
    public ResponseEntity<Boolean> isFollowed(@Valid @RequestParam Long followerId, @RequestParam Long groupId){
        Boolean response = followGroupService.isFollowed(followerId, groupId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/follow")
    public ResponseEntity<FollowerResponse> follow(@Valid @RequestBody FollowToGroupRequest request) {
        FollowGroup followGroup = followGroupService.follow(request);
        FollowerResponse response = modelMapper.map(followGroup.getFollower(), FollowerResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity unfollow(@Valid @RequestParam Long userId, @RequestParam Long groupId) {
        followGroupService.unfollow(userId, groupId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}