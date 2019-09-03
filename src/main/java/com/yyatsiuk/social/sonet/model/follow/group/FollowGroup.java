package com.yyatsiuk.social.sonet.model.follow.group;

import com.yyatsiuk.social.sonet.model.Group;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_group")
@IdClass(FollowGroupPrimaryKey.class)
public class FollowGroup implements BaseEntity {

    @Id
    @Column(name = "user_id")
    private Long followerId;

    @Id
    @Column(name = "group_id")
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name ="group_id", insertable = false, updatable = false)
    private Group group;

}
