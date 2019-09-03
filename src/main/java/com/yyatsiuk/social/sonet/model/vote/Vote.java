package com.yyatsiuk.social.sonet.model.vote;

import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.Poll;
import com.yyatsiuk.social.sonet.model.Choice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(VotePrimaryKey.class)
@Table(name = "votes")
public class Vote {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "poll_id", insertable = false, updatable = false)
    private Poll poll;

    @ManyToOne
    @JoinColumn(
            name = "сhoice_id",
            referencedColumnName = "id"
    )
    private Choice choice;


}
