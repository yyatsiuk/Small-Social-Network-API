package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.model.Actor;

public interface ActorService {

    Actor findById(Long id);

    boolean existsByNickname(String nickname);

    Actor save(Actor actor);
}
