package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Actor;
import com.yyatsiuk.social.sonet.repository.ActorRepo;
import com.yyatsiuk.social.sonet.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActorServiceImpl implements ActorService {

    private ActorRepo actorRepo;

    @Autowired
    public ActorServiceImpl(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    @Override
    public Actor findById(Long id) {
        return actorRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Actor with id:{0} not found", id));
    }

    @Override
    public Actor save(Actor actor) {
        return actorRepo.save(actor);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return actorRepo.existsByNickname(nickname);
    }
}
