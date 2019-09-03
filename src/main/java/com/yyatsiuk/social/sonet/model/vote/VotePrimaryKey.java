package com.yyatsiuk.social.sonet.model.vote;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
public class VotePrimaryKey implements Serializable {

    private Long user;
    private Long poll;

    public VotePrimaryKey(Long user_id, Long poll_id) {
        this.user = user_id;
        this.poll = poll_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VotePrimaryKey)) return false;
        VotePrimaryKey that = (VotePrimaryKey) o;
        return getUser().equals(that.getUser()) &&
                getPoll().equals(that.getPoll());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPoll());
    }
}

