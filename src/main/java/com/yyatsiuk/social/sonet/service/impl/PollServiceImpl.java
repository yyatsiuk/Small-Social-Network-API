package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.PollAddRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Poll;
import com.yyatsiuk.social.sonet.repository.PollRepo;
import com.yyatsiuk.social.sonet.service.PollService;
import com.yyatsiuk.social.sonet.service.PostService;
import com.yyatsiuk.social.sonet.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private PollRepo pollRepo;
    private ChoiceService choiceService;
    private PostService postService;

    @Autowired
    public PollServiceImpl(PollRepo pollRepo,
                           ChoiceService choiceService,
                           PostService postService) {
        this.pollRepo = pollRepo;
        this.choiceService = choiceService;
        this.postService = postService;
    }

    @Override
    @Transactional
    public Poll create(PollAddRequest pollAddRequest) {
        Poll poll = new Poll();
        poll.setQuestion(pollAddRequest.getQuestion());
        poll.setPost(postService.findById(pollAddRequest.getPostId()));
        poll.setChoices(pollAddRequest.getChoices().stream()
                .map(s -> choiceService.add(s))
                .collect(Collectors.toList()));
        return pollRepo.save(poll);
    }

    @Override
    public Poll findById(Long id) throws EntityNotFoundException {
        return pollRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Poll with id:{0} not found", id));
    }


    @Override
    @Transactional
    public void deleteById(Long id) throws EntityNotFoundException {
        try {
            pollRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Poll with id:{0} not found", id);
        }
    }

    @Override
    public List<Poll> findAllByPostId(Long postId) {
        return pollRepo.findPollsByPostId(postId);
    }


}
