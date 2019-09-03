package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChoiceRepo extends JpaRepository<Choice, Long> {

}
