package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Group;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroup;
import com.yyatsiuk.social.sonet.model.follow.group.FollowGroupPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowGroupRepo extends JpaRepository<FollowGroup, FollowGroupPrimaryKey> {

    List<FollowGroup> findByGroup(Group group);

    Page<FollowGroup> findByGroupId(Long groupId, Pageable pageable);

    Page<FollowGroup> findByFollowerId(Long userId, Pageable pageable);

    boolean existsByFollowerIdAndGroupId(Long followerId, Long groupId);


}

