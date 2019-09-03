package com.yyatsiuk.social.sonet.model.vote;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VoteResult {

    private Long choiceId;

    private Integer percent;

    private Long countOfVoice;

    public VoteResult(Long choiceId, Long countOfVoice, Integer percent) {
        this.countOfVoice  = countOfVoice;
        this.percent = percent;
        this.choiceId = choiceId;
    }


}
