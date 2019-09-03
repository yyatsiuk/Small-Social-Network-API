package com.yyatsiuk.social.sonet.model.like;

import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@IdClass(LikePrimaryKey.class)
@Table(name = "likes")
public class Like implements BaseEntity{

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "creation_time")
    @DateTimeFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    private LocalDateTime creationTime;

}

