package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.model.entity.IdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "choices")
public class Choice extends IdEntity {

    @Column(name = "text")
    @Type(type = "text")
    private String text;

}
