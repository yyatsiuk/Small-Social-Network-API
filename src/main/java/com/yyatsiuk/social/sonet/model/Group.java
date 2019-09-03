package com.yyatsiuk.social.sonet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "groups")
@PrimaryKeyJoinColumn(name = "actor_id")
public class Group extends Actor {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany(mappedBy = "groups")
    private List<User> members;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("description", description)
                .append("creator", creator.getId());
    }
}
