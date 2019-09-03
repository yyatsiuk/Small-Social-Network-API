package com.yyatsiuk.social.sonet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.social.sonet.dto.model.DTO;
import com.yyatsiuk.social.sonet.model.vote.VoteResult;
import lombok.Data;
import java.util.List;

@Data
public class VoteAddResponse implements DTO {

    private List<VoteResult> percents;

    @JsonProperty("user_choice")
    private Long userChoice;

}
