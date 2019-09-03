package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepo extends JpaRepository<Actor, Long> {

    boolean existsByNickname(String nickname);

}
