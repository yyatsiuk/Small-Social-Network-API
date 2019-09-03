package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.model.entity.CreatedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "polls")
public class Poll extends CreatedEntity {

    @Column(name = "question")
    private String question;

    @OneToMany
    @JoinTable(
            name = "poll_choices",
            joinColumns = @JoinColumn(name = "poll_id"),
            inverseJoinColumns = @JoinColumn(name = "choice_id")
    )
    private List<Choice> choices;

    @ManyToOne
    @JoinTable(
            name = "post_polls",
            joinColumns = @JoinColumn(name = "poll_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Post post;

}
