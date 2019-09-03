package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.PollAddRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Poll;

import java.util.List;

public interface PollService {

    Poll create (PollAddRequest pollAddRequest);

    Poll findById (Long id) throws EntityNotFoundException;

    void deleteById(Long pollId) throws EntityNotFoundException;

    List<Poll> findAllByPostId(Long postId);

}
