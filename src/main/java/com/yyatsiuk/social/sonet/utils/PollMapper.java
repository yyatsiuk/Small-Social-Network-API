package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.PollDTO;
import com.yyatsiuk.social.sonet.model.Poll;
import com.yyatsiuk.social.sonet.model.Choice;
import com.yyatsiuk.social.sonet.model.entity.IdEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component("pollMapper")
public class PollMapper implements CustomModelMapper<Poll, PollDTO> {

    @Override
    public PollDTO toDTO(Poll entity) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setQuestion(entity.getQuestion());
        pollDTO.setId(entity.getId());
        pollDTO.setChoices(entity.getChoices().stream()
                .collect(Collectors
                .toMap(IdEntity::getId, Choice::getText)));
        return pollDTO;
    }

}
