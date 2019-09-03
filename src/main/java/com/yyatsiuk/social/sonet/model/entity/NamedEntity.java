package com.yyatsiuk.social.sonet.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor

@MappedSuperclass
public abstract class NamedEntity extends CreatedEntity {

    @Column(name = "name")
    protected String name;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("name", name);
    }
}
