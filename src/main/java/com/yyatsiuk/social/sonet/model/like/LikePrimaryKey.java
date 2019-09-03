package com.yyatsiuk.social.sonet.model.like;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikePrimaryKey implements Serializable {

    private Long user;
    private Long post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikePrimaryKey)) return false;
        LikePrimaryKey that = (LikePrimaryKey) o;
        return getUser().equals(that.getUser()) &&
                getPost().equals(that.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPost());
    }
}
