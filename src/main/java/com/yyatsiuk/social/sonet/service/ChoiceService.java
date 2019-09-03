package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Choice;

public interface ChoiceService {

    Choice add(String description);

    Choice findById(Long id) throws EntityNotFoundException;

}
