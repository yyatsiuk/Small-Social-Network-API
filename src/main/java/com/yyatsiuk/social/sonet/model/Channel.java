package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.model.entity.IdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "channels")
public class Channel extends IdEntity {

    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @OneToMany(mappedBy = "channel")
    private List<Message> messages;
}
