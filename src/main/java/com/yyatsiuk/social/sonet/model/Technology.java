package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.model.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "technologies")
public class Technology extends NamedEntity {
}
