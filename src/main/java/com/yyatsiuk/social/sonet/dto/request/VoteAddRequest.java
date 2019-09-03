package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.social.sonet.dto.model.DTO;
import lombok.Data;

@Data
public class VoteAddRequest implements DTO {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("poll_id")
    private Long pollId;

    @JsonProperty("choice_id")
    private Long choiceId;

}
