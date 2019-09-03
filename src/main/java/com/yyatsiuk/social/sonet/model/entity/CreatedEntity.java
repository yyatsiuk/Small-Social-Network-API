package com.yyatsiuk.social.sonet.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@MappedSuperclass
public abstract class CreatedEntity extends IdEntity {


    @Column(name = "creation_time")
    @DateTimeFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    protected LocalDateTime creationTime ;


    @Column(name = "update_time")
    @DateTimeFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    protected LocalDateTime updateTime;

    @PrePersist
    public void beforeSave() {
        creationTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        updateTime = LocalDateTime.now();
    }

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("creationTime", getCreationTime())
                .append("lastUpdateTime", getUpdateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationTime);
    }
}
