package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollowPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<UserFollow, UserFollowPrimaryKey> {

    List<UserFollow> findByFollowerId(Long followerId);

    List<UserFollow> findByFollowingId(Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
