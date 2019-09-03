package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.VoteAddRequest;
import com.yyatsiuk.social.sonet.dto.response.VoteAddResponse;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.vote.Vote;

public interface VoteService {

    Vote saveVote(VoteAddRequest voteAddRequest);

    void deleteById(Long userId, Long pollId) throws EntityNotFoundException;

    VoteAddResponse vote (VoteAddRequest voteAddRequest);

    Boolean isChoiceExist (Long userId, Long pollId);

    VoteAddResponse findVoteByUserIdAndPollId(Long userId, Long pollId);

}
