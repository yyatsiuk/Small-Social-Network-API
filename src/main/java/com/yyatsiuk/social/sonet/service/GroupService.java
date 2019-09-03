package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.CreateGroupRequest;
import com.yyatsiuk.social.sonet.dto.request.UpdateGroupRequest;
import com.yyatsiuk.social.sonet.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    Group create(CreateGroupRequest createGroupRequest);

    void delete(Long groupId);

    Group findById(Long groupId);

    Page<Group> getAllGroups(Pageable pageable);

    Page<Group> findByName(String groupName, Pageable pageable);

    Page<Group> findByCurrentUserId(Long userId, Pageable pageable);

    Group update(UpdateGroupRequest updateRequest, Long groupId);

    Boolean isGroupCreator(Long userId, Long groupId);
}
