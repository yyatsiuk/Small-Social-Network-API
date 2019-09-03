package com.yyatsiuk.social.sonet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private long id;

    @Column(name  ="token")
    private String token;

    @OneToOne()
    @JoinColumn(name = "actor_id")
    private User user;


}
