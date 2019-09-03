package com.yyatsiuk.social.sonet.model.follow.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserFollowPrimaryKey implements Serializable {

    private Long followerId;

    private Long followingId;

    public UserFollowPrimaryKey(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFollowPrimaryKey)) return false;
        UserFollowPrimaryKey that = (UserFollowPrimaryKey) o;
        return getFollowerId().equals(that.getFollowerId()) &&
                getFollowingId().equals(that.getFollowingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFollowerId(), getFollowingId());
    }
}

