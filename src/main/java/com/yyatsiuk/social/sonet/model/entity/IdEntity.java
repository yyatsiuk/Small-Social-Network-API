package com.yyatsiuk.social.sonet.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@MappedSuperclass
public abstract class IdEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    protected ToStringCreator getToStringCreator() {
        return new ToStringCreator(this)
                .append("id", getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof IdEntity)) {
            return false;
        }

        IdEntity entity = (IdEntity) obj;
        return Objects.equals(id, entity.id);
    }

    @Override
    public String toString() {
        return getToStringCreator().toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
