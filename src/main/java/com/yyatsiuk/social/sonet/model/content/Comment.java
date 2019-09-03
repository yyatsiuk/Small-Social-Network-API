package com.yyatsiuk.social.sonet.model.content;

import com.yyatsiuk.social.sonet.model.Actor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends Content {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> likes;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("post", getPost().getId());
    }
}
