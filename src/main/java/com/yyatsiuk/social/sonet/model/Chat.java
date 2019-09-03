package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.entity.NamedEntity;
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
@Table(name = "chats")
public class Chat extends NamedEntity {

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Actor creator;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany(mappedBy = "chats")
    private List<User> members;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.PERSIST)
    private List<Channel> channels;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("creator", creator.getId())
                .append("image", image.getId());
    }

    public String getNameForTwoUsers(Long userId){
        return members
                .stream()
                .filter(member -> !member.getId().equals(userId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new)
                .getFullName();
    }
}
