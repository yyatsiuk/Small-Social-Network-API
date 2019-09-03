package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.CreateGroupRequest;
import com.yyatsiuk.social.sonet.dto.request.UpdateGroupRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.exception.NicknameAlreadyExistException;
import com.yyatsiuk.social.sonet.model.Group;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.repository.GroupRepo;
import com.yyatsiuk.social.sonet.service.ActorService;
import com.yyatsiuk.social.sonet.service.GroupService;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class GroupServiceImpl implements GroupService {

    @Value("${default.avatar.url}")
    private String defaultImageUrl;
    @Value("${default.background.url}")
    private String defaultBackgroundUrl;

    private final GroupRepo groupRepo;
    private final UserService userService;
    private final ImageService imageService;
    private final ActorService actorService;

    public GroupServiceImpl(
            GroupRepo groupRepo,
            UserService userService,
            ImageService imageService,
            ActorService actorService
    ) {
        this.groupRepo = groupRepo;
        this.userService = userService;
        this.imageService = imageService;
        this.actorService = actorService;
    }


    @Override
    @Transactional
    public Group create(CreateGroupRequest createRequest) {
        if (actorService.existsByNickname(createRequest.getNickname())) {
            throw new NicknameAlreadyExistException("Group with nickname: {0} already exists",
                    createRequest.getNickname());
        }

        Group group = new Group();
        Image defaultAvatar = imageService.findByUrl(defaultImageUrl);
        Image defaultBackground = imageService.findByUrl(defaultBackgroundUrl);

        if (StringUtils.isEmpty(createRequest.getBackgroundURL())) {
            group.setBackground(defaultBackground);
        } else {
            group.setBackground(imageService.findByUrl(createRequest.getBackgroundURL()));
        }

        if (StringUtils.isEmpty(createRequest.getAvatarURL())) {
            group.setAvatar(defaultAvatar);
        } else {
            group.setAvatar(imageService.findByUrl(createRequest.getAvatarURL()));
        }

        group.setStatus(Status.ACTIVE);
        group.setCreator(userService.getUserById(createRequest.getCreatorId()));
        group.setDescription(createRequest.getDescription());
        group.setName(createRequest.getName());
        group.setNickname(createRequest.getNickname());

        return groupRepo.save(group);
    }

    @Override
    public void delete(Long groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Group with id:{0} not found", groupId));

        group.setStatus(Status.DELETED);
        groupRepo.save(group);
    }

    @Override
    public Page<Group> getAllGroups(Pageable pageable) {
        return groupRepo.findAllByStatus(pageable);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Group> findByName(String groupName, Pageable pageable) {
        return groupRepo.findByNameAndStatus(groupName, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Group> findByCurrentUserId(Long userId, Pageable pageable) {
        return groupRepo.findAllCurrentUserGroups(userId, pageable);
    }

    @Override
    public Group update(UpdateGroupRequest updateRequest, Long groupId) {
        Group group = findById(groupId);

        group.setDescription(updateRequest.getDescription());
        group.setName(updateRequest.getName());
        group.setNickname(updateRequest.getNickname());
        group.setAvatar(imageService.findByUrl(updateRequest.getAvatarURL()));
        group.setBackground(imageService.findByUrl(updateRequest.getBackgroundURL()));

        return groupRepo.save(group);
    }

    @Override
    public Boolean isGroupCreator(Long userId, Long groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Group with id:{0} not found", groupId));
        return userId.equals(group.getCreator().getId());
    }

    @Override
    public Group findById(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Group with id:{0} not found", groupId));
    }

}
