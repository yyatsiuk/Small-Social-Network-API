package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Choice;
import com.yyatsiuk.social.sonet.repository.ChoiceRepo;
import com.yyatsiuk.social.sonet.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private ChoiceRepo choiceRepo;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepo choiceRepo) {
        this.choiceRepo = choiceRepo;
    }

    @Override
    public Choice add(String description) {
        Choice choice = new Choice();
        choice.setText(description);
        choice = choiceRepo.save(choice);
        return choice;
    }

    @Override
    public Choice findById(Long id) throws EntityNotFoundException {
        return choiceRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Choice with id:{0} not found", id));
    }

}
