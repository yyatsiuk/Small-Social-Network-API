package com.yyatsiuk.social.sonet.model.content;

import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.entity.CreatedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@MappedSuperclass
public abstract class Content extends CreatedEntity {

    @PrePersist
    public void beforeSave(){
        this.setStatus(Status.ACTIVE);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected Status status;

    @Column(name = "text")
    @Type(type = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("status", status)
                .append("text", text)
                .append("creator", creator.getId());
    }
}
