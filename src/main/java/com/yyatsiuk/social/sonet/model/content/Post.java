package com.yyatsiuk.social.sonet.model.content;

import com.yyatsiuk.social.sonet.model.Actor;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Poll;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends Content {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Actor owner;

    @ManyToMany
    @JoinTable(
            name = "post_images",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> likes;

    @OneToMany
    @JoinTable(
            name = "post_polls",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "poll_id")
    )
    private List<Poll> polls;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("owner", getOwner().getId());
    }
}
